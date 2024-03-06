package piperkt.services.servers.domain

interface ServerRepository {
    fun save(serverName: String, serverDescription: String, owner: String): Server

    fun findByMember(memberUsername: String): List<Server>

    fun deleteServer(serverId: String)

    fun updateServer(serverId: String, serverName: String?, serverDescription: String?): Server?

    fun getServerMembers(serverId: String): List<String>

    fun addServerMember(serverId: String, member: String): Server?

    fun removeServerMember(serverId: String, member: String): Server?
}
