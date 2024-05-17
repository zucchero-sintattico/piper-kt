package mocks.publishers

import piperkt.common.mocks.MockedEventPublisher
import piperkt.events.UserEvent
import piperkt.events.UserEventPublisher

class MockedUserEventPublisher : MockedEventPublisher<UserEvent>(), UserEventPublisher
