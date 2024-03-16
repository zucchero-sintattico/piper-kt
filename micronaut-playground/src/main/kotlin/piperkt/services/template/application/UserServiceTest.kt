package piperkt.services.template.application

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class UserServiceTest {

    private lateinit var userService: UserService

    @BeforeEach
    fun setUp() {
        userService = UserService()
    }

    @Test
    fun testExample() {
        // TODO: Add test logic here
        assertEquals(2, userService.add(1, 1))
    }
}