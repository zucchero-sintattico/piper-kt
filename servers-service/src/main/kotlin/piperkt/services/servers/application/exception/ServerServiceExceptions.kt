package piperkt.services.servers.application.exception

class ServerNotFoundException : RuntimeException("Server not found")

class UserNotInServerException : RuntimeException("User not in server")
