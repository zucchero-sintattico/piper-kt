package mocks.publishers

import piperkt.common.MockedEventPublisher
import piperkt.common.events.SessionEvent
import piperkt.common.events.SessionEventPublisher

class MockedSessionEventPublisher : SessionEventPublisher, MockedEventPublisher<SessionEvent>()
