package piperkt.services.servers.application

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import piperkt.services.commons.domain.id.ChannelId
import piperkt.services.commons.domain.id.ServerId
import piperkt.services.servers.application.api.command.CommandResponse
import piperkt.services.servers.application.api.command.CreateNewChannelInServerRequest
import piperkt.services.servers.application.api.command.DeleteChannelInServerRequest
import piperkt.services.servers.application.api.command.UpdateChannelInServerRequest
import piperkt.services.servers.domain.factory.ChannelFactory

class ChannelServiceTest : AnnotationSpec() {
    private val channelRepository = mock<ChannelRepository>()
    private val serverService = ChannelService(channelRepository)
    private val fakeServerId = ServerId("000000000000000000000000")
    private val fakeChannelId = ChannelId("000000000000000000000000")
    private val fakeChannel =
        ChannelFactory.createFromType(
            fakeChannelId.value,
            "channelName",
            "channelDescription",
            "TEXT"
        )

    @Test
    fun `should allow to create a channel`() {
        whenever(channelRepository.save(any(), any(), any(), any())).thenReturn(fakeChannel)
        val request =
            CreateNewChannelInServerRequest(
                fakeServerId,
                "channelName",
                "channelDescription",
                "TEXT"
            )
        serverService.createNewChannelInServer(request) shouldBe CommandResponse(true)
    }

    @Test
    fun `should not allow to create a channel if server doesn't exist`() {
        whenever(channelRepository.save(any(), any(), any(), any())).thenReturn(null)
        val request =
            CreateNewChannelInServerRequest(
                fakeServerId,
                "channelName",
                "channelDescription",
                "TEXT"
            )
        serverService.createNewChannelInServer(request) shouldBe CommandResponse(false)
    }

    @Test
    fun `should allow to update a channel`() {
        whenever(channelRepository.updateChannel(any(), any(), any(), any()))
            .thenReturn(fakeChannel)
        serverService.updateChannelInServer(
            UpdateChannelInServerRequest(
                fakeServerId,
                fakeChannelId,
                "channelName",
                "channelDescription",
                "TEXT"
            )
        ) shouldBe CommandResponse(true)
    }

    @Test
    fun `should not allow to update a channel if server or channel don't exist`() {
        whenever(channelRepository.updateChannel(any(), any(), any(), any())).thenReturn(null)
        serverService.updateChannelInServer(
            UpdateChannelInServerRequest(
                fakeServerId,
                fakeChannelId,
                "channelName",
                "channelDescription",
                "TEXT"
            )
        ) shouldBe CommandResponse(false)
    }

    @Test
    fun `should allow to delete a channel`() {
        whenever(channelRepository.delete(any(), any())).thenReturn(true)
        serverService.deleteChannelInServer(
            DeleteChannelInServerRequest(fakeServerId, fakeChannelId)
        ) shouldBe CommandResponse(true)
    }

    @Test
    fun `should not allow to delete a channel if server or channel don't exist`() {
        whenever(channelRepository.delete(any(), any())).thenReturn(false)
        serverService.deleteChannelInServer(
            DeleteChannelInServerRequest(fakeServerId, fakeChannelId)
        ) shouldBe CommandResponse(false)
    }
}
