package mocks.publishers

import piperkt.services.multimedia.common.MockedEventPublisher
import piperkt.services.multimedia.domain.session.SessionEvent
import piperkt.services.multimedia.domain.session.SessionEventPublisher

class MockedSessionEventPublisher : SessionEventPublisher, MockedEventPublisher<SessionEvent>()
