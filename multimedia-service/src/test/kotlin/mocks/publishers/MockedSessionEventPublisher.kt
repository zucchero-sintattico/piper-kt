package mocks.publishers

import piperkt.common.mocks.MockedEventPublisher
import piperkt.events.SessionEvent
import piperkt.events.SessionEventPublisher

class MockedSessionEventPublisher : SessionEventPublisher, MockedEventPublisher<SessionEvent>()
