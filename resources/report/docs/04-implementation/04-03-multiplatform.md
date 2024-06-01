# Multiplatform Events

## Kotlin Multiplatform

Kotlin Multiplatform is a technology that allows you to share code between different platforms.
It is a great way to reduce the amount of code that needs to be written for each platform.

The Project uses Kotlin Multiplatform to share the `events` module between Kotlin backends and Typescript backend.
This allows a single point of truth for definition of domain events.

Here an example of a shared module with notation to let export the defined classes:

```kotlin
@JsExport sealed interface ServerEvent : DomainEvent

@JsExport data class ServerCreatedEvent(val serverId: String, val owner: String) : ServerEvent

@JsExport data class ServerUpdatedEvent(val serverId: String) : ServerEvent

@JsExport data class ServerUserAddedEvent(val serverId: String, val username: String) : ServerEvent
```

A drawback of this approach is that nested classes cannot be exported, so classes have been flattened to avoid this issue.

Other useful tips for Kotlin Multiplatform configuration and typescript usage are:

- `optIn("kotlin.js.ExperimentalJsExport")`: This annotation allows to export classes and interfaces to javascript without specifying it in every file.
- `generateTypeScriptDefinitions()`: This function generates a `.d.ts` file with the type definitions of the exported classes and interfaces, allowing to use them in typescript code.

### How to use from TS

To use the shared module from Typescript, you need to add the generated or add it as a dependency in your project.
Then you can import the classes and interfaces, specifying full package name:

```typescript
import {piperkt} from "./events-lib";

const event = new piperkt.events.ServerCreatedEvent("serverId", "pippo")
```

### How to generate the JS library

To generate the JS library, you can use the following gradle task:

```shell
./gradlew :events:jsNodeProductionLibraryDistribution
```

It will output the file in `events/build/dist/js/productionLibrary` directory.

The generation of the JS library and import of the dependencies has been automated during the build of `notification-service`.

## NPM publishing

The generated JS library has been published on NPM.

You can find it on [NPM Registry](https://www.npmjs.com/package/@zucchero-sintattico/events).
