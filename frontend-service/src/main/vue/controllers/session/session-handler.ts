import type { Socket } from "socket.io-client";

type UserJoinCallback = (username: string, stream: MediaStream) => void;
type UserLeaveCallback = (username: string) => void;

export interface SessionHandler {
  start(
    myStream: MediaStream,
    onUserJoin: UserJoinCallback,
    onUserLeave: UserLeaveCallback
  ): void;

  stop(): void;
}

const WebRtcConfiguration: RTCConfiguration = {
  iceServers: [
    {
      urls: "stun:stun.relay.metered.ca:80",
    },
    {
      urls: "turn:standard.relay.metered.ca:80",
      username: "a11c5588067f957f8ac7c961",
      credential: "+XRUMIx/UlxBHGZK",
    },
    {
      urls: "turn:standard.relay.metered.ca:80?transport=tcp",
      username: "a11c5588067f957f8ac7c961",
      credential: "+XRUMIx/UlxBHGZK",
    },
    {
      urls: "turn:standard.relay.metered.ca:443",
      username: "a11c5588067f957f8ac7c961",
      credential: "+XRUMIx/UlxBHGZK",
    },
    {
      urls: "turns:standard.relay.metered.ca:443?transport=tcp",
      username: "a11c5588067f957f8ac7c961",
      credential: "+XRUMIx/UlxBHGZK",
    },
  ],
};

export class SessionHandlerImpl implements SessionHandler {
  private sessionId: string;
  private socket: Socket;
  private clientUsername: string;

  private myStream?: MediaStream;
  private onUserJoin?: UserJoinCallback;
  private onUserLeave?: UserLeaveCallback;

  private peers: Record<string, RTCPeerConnection> = {};

  constructor(socket: Socket, sessionid: string, clientUsername: string) {
    this.socket = socket;
    this.sessionId = sessionid;
    this.clientUsername = clientUsername;
  }

  start(
    myStream: MediaStream,
    onUserJoin: UserJoinCallback,
    onUserLeave: UserLeaveCallback
  ): void {
    this.myStream = myStream;
    this.onUserJoin = onUserJoin;
    this.onUserLeave = onUserLeave;
    this.setupProtocolListener();
    console.log("Joining session", this.sessionId);
    this.socket.emit("join", { sessionId: this.sessionId });
  }

  stop(): void {
    this.socket.disconnect();
    this.socket.close();
  }

  private setupProtocolListener() {
    this.socket.on("user-join", async (data) => {
      try {
        console.log("[SessionHandler] User joined:", data);
        const parsed = JSON.parse(data);
        const userId = parsed["userId"] as string;
        await this.onUserConnected(userId);
      } catch (e) {
        console.error(e);
      }
    });
    this.socket.on("user-left", async (data) => {
      try {
        console.log("[SessionHandler] User left:", data);
        const parsed = JSON.parse(data);
        const username = parsed["userId"] as string;
        await this.onUserDisconnected(username);
      } catch (e) {
        console.error(e);
      }
    });
    this.socket.on("offer-received", async (data) => {
      try {
        console.log("[SessionHandler] Received offer:", data);
        const offer = data["offer"] as RTCSessionDescriptionInit;
        const from = data["from"] as string;
        await this.onOffer(offer, from);
      } catch (e) {
        console.error(e);
      }
    });
    this.socket.on("answer-received", async (data) => {
      try {
        console.log("[SessionHandler] Received answer:", data);
        const answer = data["answer"] as RTCSessionDescriptionInit;
        const from = data["from"] as string;
        await this.onAnswer(answer, from);
      } catch (e) {
        console.error(e);
      }
    });
    this.socket.on("candidate-received", async (data) => {
      try {
        console.log("[SessionHandler] Received ice candidate:", data);
        const candidate = data["candidate"] as RTCIceCandidate;
        const from = data["from"] as string;
        await this.onIceCandidate(candidate, from);
      } catch (e) {
        console.error(e);
      }
    });
  }

  private async onUserConnected(username: string) {
    console.log("[SessionHandler] User connected:", username);
    this.peers[username] = new RTCPeerConnection(WebRtcConfiguration);

    this.myStream!.getTracks().forEach((track) => {
      console.log("[SessionHandler] Adding track to:", username);
      this.peers[username].addTrack(track, this.myStream!);
    });

    this.peers[username].ontrack = (event) => {
      this.onUserJoin?.(username, event.streams[0]);
    };

    const offer = await this.peers[username].createOffer();
    await this.peers[username].setLocalDescription(offer);

    console.log("[SessionHandler] Sending offer to:", username);
    this.socket.emit("offer", {
      offer: JSON.stringify({
        type: offer.type,
        sdp: offer.sdp,
      }),
      to: username,
      from: this.clientUsername,
    });

    this.peers[username].onicecandidate = (event) => {
      if (event.candidate) {
        console.log("[SessionHandler] Sending ice candidate to:", username);
        this.socket.emit("candidate", {
          candidate: JSON.stringify(event.candidate),
          to: username,
          from: this.clientUsername,
        });
      }
    };
  }

  private async onUserDisconnected(username: string) {
    console.log("[SessionHandler] User disconnected:", username);
    this.onUserLeave?.(username);
    this.peers[username]?.close();
    delete this.peers[username];
  }

  private async onOffer(offer: RTCSessionDescriptionInit, from: string) {
    console.log("[SessionHandler] Received offer from:", from);
    this.peers[from] = new RTCPeerConnection(WebRtcConfiguration);
    this.peers[from].ontrack = (event) => {
      this.onUserJoin?.(from, event.streams[0]);
    };
    this.peers[from].onicecandidate = (event) => {
      if (event.candidate) {
        console.log("[SessionHandler] Sending ice candidate to:", from);
        this.socket.emit("candidate", {
          candidate: JSON.stringify(event.candidate),
          to: from,
          from: this.clientUsername,
        });
      }
    };
    this.myStream!.getTracks().forEach((track) => {
      this.peers[from].addTrack(track, this.myStream!);
    });
    await this.peers[from].setRemoteDescription(offer);
    const answer = await this.peers[from].createAnswer();
    await this.peers[from].setLocalDescription(answer);
    console.log("[SessionHandler] Sending answer to:", from);
    this.socket.emit("answer", {
      answer: JSON.stringify({
        type: answer.type,
        sdp: answer.sdp,
      }),
      to: from,
      from: this.clientUsername,
    });
  }

  private async onAnswer(answer: RTCSessionDescriptionInit, from: string) {
    console.log("[SessionHandler] Received answer from:", from);
    await this.peers[from].setRemoteDescription(answer);
  }

  private async onIceCandidate(candidate: RTCIceCandidate, from: string) {
    console.log("[SessionHandler] Received ice candidate from:", from);
    await this.peers[from].addIceCandidate(candidate);
  }
}
