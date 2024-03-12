package piperkt.services.multimedia.domain.servers

import piperkt.services.multimedia.domain.channels.Channel
import piperkt.services.multimedia.domain.users.User

class Server(val id: ServerId, val channels: List<Channel>, val users: List<User>)
