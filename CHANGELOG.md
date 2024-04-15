## [2.0.0](https://github.com/zucchero-sintattico/piper-kt/compare/v1.13.1...v2.0.0) (2024-04-15)


### ⚠ BREAKING CHANGES

* **ddd:** changed domain and application in order to follow ddd best practices
* **services:** added multimedia service

### Features

* added UUIDEntityId ([e6d6ac4](https://github.com/zucchero-sintattico/piper-kt/commit/e6d6ac42fd34fe2ef4e8cd02337b7ad42b2c0262))
* **deps:** added socketio library to enable creation of socket.io server ([3a5e27f](https://github.com/zucchero-sintattico/piper-kt/commit/3a5e27f4ef58863c6e8d0399f933ee7663b17fd9))
* **deps:** added socketio-client in order to test the socket.io server ([0400455](https://github.com/zucchero-sintattico/piper-kt/commit/040045566eacaa9e38b3f77e39f033e0d5de94ea))
* **json:** added kotlinx.serializtion to handle json serialization/deserialization ([4fd4747](https://github.com/zucchero-sintattico/piper-kt/commit/4fd4747c55b455930eb6efd6a1bc21a4bbb399aa))
* **multimedia-web:** added controllers and security rules ([05963eb](https://github.com/zucchero-sintattico/piper-kt/commit/05963eb047fa165afa547f1b24bb73f416cd01a1))
* **multimedia-websocket:** switched to service use instead of repository ([0ff28b7](https://github.com/zucchero-sintattico/piper-kt/commit/0ff28b72a34d5e855386758918d09bcc93689232))
* **multimedia:** Added event listeners for users joining, leaving, and being kicked from servers ([500f324](https://github.com/zucchero-sintattico/piper-kt/commit/500f324cf7b7336432fdd4de2a5e849ffbe730fc))
* **multimedia:** Added Kafka dependency, implemented ServersEventsListener and ServerRepositoryImpl. ([4fca125](https://github.com/zucchero-sintattico/piper-kt/commit/4fca12559f044314026f9e1a3fae27a057ae0137))
* **multimedia:** Added MultimediaApi interface, and Result class for handling API responses ([008a09a](https://github.com/zucchero-sintattico/piper-kt/commit/008a09a03a9e87c6c32888fcd5bd0d1732e70f05))
* **multimedia:** implemented event listeners ([e185b20](https://github.com/zucchero-sintattico/piper-kt/commit/e185b20f52506464996b69ac18b5a6ad6d70fef5))
* **multimedia:** implementing multimedia service general structure ([f36d181](https://github.com/zucchero-sintattico/piper-kt/commit/f36d181751a90f53b7f1c6d0bdd7f918c716d6e0))
* **multimedia:** integrated repository operation in protocol ([78267d4](https://github.com/zucchero-sintattico/piper-kt/commit/78267d4142f0469adc5cf5a9f75c08697c97827f))
* refactor ([ef7664d](https://github.com/zucchero-sintattico/piper-kt/commit/ef7664d0d66427d748664f6214f35bbf48859930))
* **services:** added multimedia service ([8866f75](https://github.com/zucchero-sintattico/piper-kt/commit/8866f75d53775098e1fff7ea9a1395022b54dcab))
* **ws:** added token extraction and rooms managment ([b40c6c7](https://github.com/zucchero-sintattico/piper-kt/commit/b40c6c734e1940c0b325e17761b40d5a59f72260))


### Bug Fixes

* **api:** fixed error handling method signature ([f8a4680](https://github.com/zucchero-sintattico/piper-kt/commit/f8a4680673ebf9e1c117e9f518dce3437923b04c))
* micronauttest annotation ([edbb664](https://github.com/zucchero-sintattico/piper-kt/commit/edbb664010b7b1dc07a095091794cdf1d732ff91))
* **multimedia-test:** added runCatching to avoid error throwing on socket test ([04b3e78](https://github.com/zucchero-sintattico/piper-kt/commit/04b3e78ec869c1db6d045c9dc8a5717d4ba1e580))
* **multimedia-test:** removed assertion temporary from socket test to avoid bug in C.I. ([8b4f6ae](https://github.com/zucchero-sintattico/piper-kt/commit/8b4f6ae25211dc2ea43a0683b095f5bad1608e03))
* **multimedia:** fixed test that doesnt compile ([18c444a](https://github.com/zucchero-sintattico/piper-kt/commit/18c444aca1597e950e6c7908a7dacaa3645ea10a))
* **multimedia:** fixed tests ([390a81b](https://github.com/zucchero-sintattico/piper-kt/commit/390a81b0317c8488fc1b1184174e1cc79267ef02))
* **multimedia:** put random port on server startup to avoid bind exception ([e9bb298](https://github.com/zucchero-sintattico/piper-kt/commit/e9bb298b4b95365609f6cd779b8ae1e4f331a13d))
* **multimedia:** removed integration test ([673c966](https://github.com/zucchero-sintattico/piper-kt/commit/673c9669c6dcd13ad3191d720218d243bd9ad3ce))
* **project:** removed deleted dependencies ([7bcaec3](https://github.com/zucchero-sintattico/piper-kt/commit/7bcaec3c4d90a5b2cd28a8aa800e144b60206157))
* **test:** fixed architecture test ([3489838](https://github.com/zucchero-sintattico/piper-kt/commit/348983833ca84754ed6d3941a551490fe1b1faf4))
* **test:** fixed architecture test ([a5435c4](https://github.com/zucchero-sintattico/piper-kt/commit/a5435c43dd011b922e9d32c2a8cae0d72bce61db))
* **test:** fixed socketio server test setupping a session repository ([f79b803](https://github.com/zucchero-sintattico/piper-kt/commit/f79b80345066e667c519610d808220208f1d5729))
* **test:** fixed type in base test class name ([cf9e678](https://github.com/zucchero-sintattico/piper-kt/commit/cf9e678f9b5c6ad82403b13407979f4fa9cd8eed))


### Code Refactoring

* **ddd:** changed domain and application in order to follow ddd best practices ([cfdc80f](https://github.com/zucchero-sintattico/piper-kt/commit/cfdc80f1a1f37ad3f3da02cf59cd77dc243fba4b))

## [1.13.1](https://github.com/zucchero-sintattico/piper-kt/compare/v1.13.0...v1.13.1) (2024-03-08)


### Bug Fixes

* **spotless:** yaml configuration dependencies conflict ([5ccdbb2](https://github.com/zucchero-sintattico/piper-kt/commit/5ccdbb26433209f8f9ced20a9450a4307fea743c))

## [1.13.0](https://github.com/zucchero-sintattico/piper-kt/compare/v1.12.0...v1.13.0) (2024-03-07)


### Features

* **repo:** add spotless plugin ([#25](https://github.com/zucchero-sintattico/piper-kt/issues/25)) ([bc4569f](https://github.com/zucchero-sintattico/piper-kt/commit/bc4569f2a9675dde97276a9a515067312a4f18f1))


### Bug Fixes

* **ci:** adjust error in condition ([1d90c7e](https://github.com/zucchero-sintattico/piper-kt/commit/1d90c7e174ce8c3805c22c54c844623e6d066902))
* **repo:** bug in unrelated tasks -- should not be there ([2e656e4](https://github.com/zucchero-sintattico/piper-kt/commit/2e656e4d04101d19e7b62e5a28b98f006332a52b))

## [1.12.0](https://github.com/zucchero-sintattico/piper-kt/compare/v1.11.0...v1.12.0) (2024-03-05)


### Features

* **build:** add dependencies for micronaut ([5834bac](https://github.com/zucchero-sintattico/piper-kt/commit/5834bacd1f2bc253b002b1dea2a4e5f73c467023))
* **build:** add java version of gradle API ([bc17fc0](https://github.com/zucchero-sintattico/piper-kt/commit/bc17fc0e06fd79f372b6f13818c658c98e13e828))


### Bug Fixes

* **style:** corrected kfmtm and detekt errors ([19d9d70](https://github.com/zucchero-sintattico/piper-kt/commit/19d9d70a45bdec4de36565caa2c8a1afae76c05e))

## [1.11.0](https://github.com/zucchero-sintattico/piper-kt/compare/v1.10.0...v1.11.0) (2024-02-25)


### Features

* **bdd:** add acceptance tests ([071195c](https://github.com/zucchero-sintattico/piper-kt/commit/071195cae5aa05dcbd9ad79beac23e2c23177669))
* **bdd:** add cucumber dependency ([45be7e6](https://github.com/zucchero-sintattico/piper-kt/commit/45be7e6130e1a7b5a0032fa1b9924d8ff1f78d80))
* **ci:** prepare ci to deploy features report ([57c4330](https://github.com/zucchero-sintattico/piper-kt/commit/57c433087cd4e364b10cfbd904d9ae99bffa52d5))


### Bug Fixes

* **test:** try to remove deepsource antipattern warning ([66eda86](https://github.com/zucchero-sintattico/piper-kt/commit/66eda86c706eb6699bf31735a6d80ff6344370ea))

## [1.10.0](https://github.com/zucchero-sintattico/piper-kt/compare/v1.9.1...v1.10.0) (2024-02-24)


### Features

* **ddd:** add servers and channel concept in domain ([b41b046](https://github.com/zucchero-sintattico/piper-kt/commit/b41b0461339970a08cf0dec4f127e27fd7317757))
* **ddd:** add users concept in domain ([888983a](https://github.com/zucchero-sintattico/piper-kt/commit/888983a03de26d75bb7280a6b841f74a8b9c4213))
* **ddd:** init with glossary ([d398107](https://github.com/zucchero-sintattico/piper-kt/commit/d398107d0df5f1a26ebf4413a55638c382795f4e))
* **repo:** add common concept package ([97c3dda](https://github.com/zucchero-sintattico/piper-kt/commit/97c3dda8e38b99518c46fb5accc2ad85cb7009ce))

## [1.9.1](https://github.com/zucchero-sintattico/piper-kt/compare/v1.9.0...v1.9.1) (2024-02-22)


### Bug Fixes

* **ci:** avoid publishing pages if no release ([03001dc](https://github.com/zucchero-sintattico/piper-kt/commit/03001dc46ac23ece637fdc28090c941901212e68))

## [1.9.0](https://github.com/zucchero-sintattico/piper-kt/compare/v1.8.0...v1.9.0) (2024-02-13)


### Features

* **repo:** add documentation framework, built it and release on gh-pages ([90f55d2](https://github.com/zucchero-sintattico/piper-kt/commit/90f55d209af52cccf15524eb97ddc8aa7a16f396))

## [1.8.0](https://github.com/zucchero-sintattico/piper-kt/compare/v1.7.0...v1.8.0) (2024-02-12)


### Features

* **ci:** improve build, test and coverage ([c7861cd](https://github.com/zucchero-sintattico/piper-kt/commit/c7861cdedda46af36d864891fe0df8745e505e5e))

## [1.7.0](https://github.com/zucchero-sintattico/piper-kt/compare/v1.6.0...v1.7.0) (2024-02-12)


### Features

* **repo:** add coverage framework ([bf4977a](https://github.com/zucchero-sintattico/piper-kt/commit/bf4977a94dde979c4301b12590fd3c831b8afb81))

## [1.6.0](https://github.com/zucchero-sintattico/piper-kt/compare/v1.5.0...v1.6.0) (2024-02-12)


### Features

* **repo:** add architecture testing framework ([2ec8c04](https://github.com/zucchero-sintattico/piper-kt/commit/2ec8c04dfa9ffe8c97b5071d2ab9ba3971b66f21))

## [1.5.0](https://github.com/zucchero-sintattico/piper-kt/compare/v1.4.0...v1.5.0) (2024-02-11)


### Features

* **repo:** add kotest for testing ([9bcbc3f](https://github.com/zucchero-sintattico/piper-kt/commit/9bcbc3ffd1643599ff0ec3115509214775a76757))

## [1.4.0](https://github.com/zucchero-sintattico/piper-kt/compare/v1.3.0...v1.4.0) (2024-02-10)


### Features

* **deps:** add detekt ([ee61536](https://github.com/zucchero-sintattico/piper-kt/commit/ee6153671cc8f169e92544f5df45b01054168f5c))
* **domain:** init domain for ddd ([ae12d11](https://github.com/zucchero-sintattico/piper-kt/commit/ae12d1161bdb97f5df79c1be3a5436b159b45578))
* **repo:** add precommit checks and common build logic ([be211ae](https://github.com/zucchero-sintattico/piper-kt/commit/be211aec68e1f887308e495fe24c1096509c7257))

## [1.3.0](https://github.com/zucchero-sintattico/piper-kt/compare/v1.2.0...v1.3.0) (2024-02-10)


### Features

* **semantic-release:** add npm plugin ([1637d20](https://github.com/zucchero-sintattico/piper-kt/commit/1637d20ad623ba20f5218d8c45a71ad2d53b198c))

## [1.2.0](https://github.com/zucchero-sintattico/piper-kt/compare/v1.1.0...v1.2.0) (2024-02-10)


### Features

* add changelog plugin ([6999166](https://github.com/zucchero-sintattico/piper-kt/commit/699916603f0f65a97c5bf4dfbee490fc445c072c))
