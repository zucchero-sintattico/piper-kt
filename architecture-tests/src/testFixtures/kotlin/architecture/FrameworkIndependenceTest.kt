package architecture

/**
 * Base class for framework independence tests.
 *
 * @param prefix The prefix of the package names.
 */
abstract class FrameworkIndependenceTest(prefix: String) : ArchitectureSpec(prefix) {

    private val frameworks = listOf("io.micronaut", "jakarta")

    @Test
    fun `domain layer doesn't depend on frameworks`() {
        assertPackageDoesNotDependOnFrameworks("domain", frameworks)
    }

    @Test
    fun `application layer doesn't depend on frameworks`() {
        assertPackageDoesNotDependOnFrameworks("application", frameworks)
    }
}
