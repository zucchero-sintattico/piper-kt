package mocks.publishers

import piperkt.common.MockedEventPublisher
import piperkt.services.multimedia.domain.session.SessionEvent
import piperkt.services.multimedia.domain.session.SessionEventPublisher

class MockedSessionEventPublisher : SessionEventPublisher, MockedEventPublisher<SessionEvent>()
