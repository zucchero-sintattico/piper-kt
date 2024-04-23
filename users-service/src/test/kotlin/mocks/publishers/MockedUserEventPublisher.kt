package mocks.publishers

import piperkt.common.MockedEventPublisher
import piperkt.services.users.domain.user.UserEvent
import piperkt.services.users.domain.user.UserEventPublisher

class MockedUserEventPublisher : MockedEventPublisher<UserEvent>(), UserEventPublisher