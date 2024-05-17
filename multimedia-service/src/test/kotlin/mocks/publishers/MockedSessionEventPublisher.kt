package mocks.publishers

import piperkt.common.events.SessionEvent
import piperkt.common.events.SessionEventPublisher
import piperkt.common.mocks.MockedEventPublisher

class MockedSessionEventPublisher : SessionEventPublisher, MockedEventPublisher<SessionEvent>()
