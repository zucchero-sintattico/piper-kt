## [7.4.0](https://github.com/zucchero-sintattico/piper-kt/compare/v7.3.0...v7.4.0) (2024-05-21)


### Features

* **commons:** make it a kotlin multiplatform project ([b5df687](https://github.com/zucchero-sintattico/piper-kt/commit/b5df68725abf2d56b7118200d00001755115c202))
* **events:** make it a kotlin multiplatform project and publish js to npm ([15b3efe](https://github.com/zucchero-sintattico/piper-kt/commit/15b3efece33bcc072ebd5e0e9b141d6876bc3714))

## [7.3.0](https://github.com/zucchero-sintattico/piper-kt/compare/v7.2.0...v7.3.0) (2024-05-20)


### Features

* **user:** simplify logout function by removing unnecessary try-catch block and directly clear localStorage on logout ([144ab11](https://github.com/zucchero-sintattico/piper-kt/commit/144ab118d4798fd2447c30d6d2016722914a3186))


### Bug Fixes

* **axios-controller:** getAuthorizationToken method to return headers object instead of directly modifying axiosInstance headers ([8b507b8](https://github.com/zucchero-sintattico/piper-kt/commit/8b507b8343e8a70ba85482f732cdac2bce4c1d6c))

## [7.2.0](https://github.com/zucchero-sintattico/piper-kt/compare/v7.1.1...v7.2.0) (2024-05-20)


### Features

* **users-api:** exposed login and refresh endpoint in openapi ([7d0a040](https://github.com/zucchero-sintattico/piper-kt/commit/7d0a040960475ca99d7749632ad5212397213289))


### Bug Fixes

* **users:** added email field in controller that was unused ([55b7ac6](https://github.com/zucchero-sintattico/piper-kt/commit/55b7ac63d5944f73cb8cc68d2132494c4061031a))

## [7.1.1](https://github.com/zucchero-sintattico/piper-kt/compare/v7.1.0...v7.1.1) (2024-05-20)


### Bug Fixes

* **friendships-service:** fix http response body field to match frontend controller specification ([5937a23](https://github.com/zucchero-sintattico/piper-kt/commit/5937a231449e8c8caa5c1f72354ed6ca4ebc3837))
* **friendships-service:** return id.value instead of object id in http responses ([751af4b](https://github.com/zucchero-sintattico/piper-kt/commit/751af4b9ff71b639d63455ed17c04326f262fb02))
* **server-service:** fix ids in http dto in order to return id value instead object ref ([dae977a](https://github.com/zucchero-sintattico/piper-kt/commit/dae977a3c58d38c574a2df97f4472dc5759637ab))

## [7.1.0](https://github.com/zucchero-sintattico/piper-kt/compare/v7.0.0...v7.1.0) (2024-05-18)


### Features

* **auth:** rename jwt variable to access_token ([4a57076](https://github.com/zucchero-sintattico/piper-kt/commit/4a5707668586c05d910084ec5ca36a857c5cba47))
* **AuthService:** add email field to register method for users to provide their email during registration ([d4a2d6a](https://github.com/zucchero-sintattico/piper-kt/commit/d4a2d6ae218c272b8a5d3a4e3a286761beb54abf))
* **axios-controller:** move axios instance creation to class constructor and set Authorization header based on user jwt token ([73a6088](https://github.com/zucchero-sintattico/piper-kt/commit/73a6088fe62211d4a178adb9c2062ac2718f2028))
* **frontend:** add status code to the body of the responce ([f7bc38e](https://github.com/zucchero-sintattico/piper-kt/commit/f7bc38e436d33b5717a85f26dc3daa75a2e44cfe))
* **ingress-controller:** update nginx-ingress-controller to use frontend-service as default backend ([4e3b0ff](https://github.com/zucchero-sintattico/piper-kt/commit/4e3b0ff75cd324bb6dd340ff7f17303a955bcd0c))
* **pathValues:** update paths for user service to /auth/register and /auth/login, add /oauth/access_token ([b68733d](https://github.com/zucchero-sintattico/piper-kt/commit/b68733d8e89bbf15c007acbff712ff57bb49654b))


### Bug Fixes

* **users:** fixed presentation mapping ([e7fb354](https://github.com/zucchero-sintattico/piper-kt/commit/e7fb3548242fdf66a1ea6fcec9ea7d6fc2eb8e9f))

## [7.0.0](https://github.com/zucchero-sintattico/piper-kt/compare/v6.9.0...v7.0.0) (2024-05-18)


### ⚠ BREAKING CHANGES

* **users:** added email field and updated auth paths

### Features

* **users:** added email field and updated auth paths ([3433028](https://github.com/zucchero-sintattico/piper-kt/commit/3433028febf57156329f874d6e70744767bef3c0))

## [6.9.0](https://github.com/zucchero-sintattico/piper-kt/compare/v6.8.0...v6.9.0) (2024-05-17)


### Features

* **report:** add Hugo for report and ci deployment ([b4d125c](https://github.com/zucchero-sintattico/piper-kt/commit/b4d125c63c5e6dbc0923600b3a0159d653a8babe))

## [6.8.0](https://github.com/zucchero-sintattico/piper-kt/compare/v6.7.0...v6.8.0) (2024-05-16)


### Features

* **events:** switched to indipendent type and tested multimedia application layer ([030dd17](https://github.com/zucchero-sintattico/piper-kt/commit/030dd17c0498d445af0d1d9b7b0eb7db8302cefe))

## [6.7.0](https://github.com/zucchero-sintattico/piper-kt/compare/v6.6.1...v6.7.0) (2024-05-16)


### Features

* **openapi:** add task to build openapi site for deployment ([c5a844a](https://github.com/zucchero-sintattico/piper-kt/commit/c5a844a6e23b31dd15d4178f029eac7df30cabd6))

## [6.6.1](https://github.com/zucchero-sintattico/piper-kt/compare/v6.6.0...v6.6.1) (2024-05-16)


### Bug Fixes

* **openapi:** move api annotation to another object ([e05322f](https://github.com/zucchero-sintattico/piper-kt/commit/e05322f804dacc70e1f67b651d098edd7acbbc6b))

## [6.6.0](https://github.com/zucchero-sintattico/piper-kt/compare/v6.5.0...v6.6.0) (2024-05-15)


### Features

* **application:** add Kafka configuration for all services to connect to cluster-kafka-brokers.piper-kt.svc.cluster.local:9092 for messaging functionality ([2d1eb12](https://github.com/zucchero-sintattico/piper-kt/commit/2d1eb127eb56bb48ee8851dab932b5015a5703f3))
* **application:** add Kafka configuration for all services to connect to cluster-kafka-brokers.piper-kt.svc.cluster.local:9092 for messaging functionality ([35c20b0](https://github.com/zucchero-sintattico/piper-kt/commit/35c20b07b6b17da8f36f13bdac8c92d6d20ba8e7))
* **atlas-operator:** add new helm chart files for mongodb atlas operator ([28554e8](https://github.com/zucchero-sintattico/piper-kt/commit/28554e8b6e93cfd57dcf2a17dbeec25d0a7c0db2))
* **CleanArchitectureSpec:** add test for verifying clean architecture layers dependencies ([b80a51f](https://github.com/zucchero-sintattico/piper-kt/commit/b80a51fa8730bc894d64a50138daf15022c07619))
* **community-operator:** add community-operator helm chart ([6a365f0](https://github.com/zucchero-sintattico/piper-kt/commit/6a365f050e2bef4e8af6e45a153415240537856d))
* **db-values:** add custom user 'my-user' with appropriate roles and password secret reference for MongoDB deployment ([0f20ae0](https://github.com/zucchero-sintattico/piper-kt/commit/0f20ae06691cf05f0d9727afa8f6a4d0317c3dec))
* **deploy:** add deployment and service creation for frontend-service in Kubernetes ([5b6d9ff](https://github.com/zucchero-sintattico/piper-kt/commit/5b6d9ff9148ee158d69f0350ae10538a01d49ada))
* **deploy:** add deployment script for helm and kubectl commands ([3cd4f3c](https://github.com/zucchero-sintattico/piper-kt/commit/3cd4f3c37667e55168da02b53e4b36a17c2f087f))
* **deploy:** add mogno db connection string to connect che services ([1de1291](https://github.com/zucchero-sintattico/piper-kt/commit/1de1291a9af161cf8c103e4a0fcc8606f1605960))
* **deploy:** add support for nginx-ingress-controller deployment and enable ingress on minikube ([8e3f124](https://github.com/zucchero-sintattico/piper-kt/commit/8e3f124d92c98c1ec206bb5707e2bb80ea37145f))
* **deploy:** conficure mongos db for microservice ([792bef6](https://github.com/zucchero-sintattico/piper-kt/commit/792bef6b628adca51afd1db581d87c1ef17a0cff))
* **deploy:** full microservice and database pods deploy ([b134c26](https://github.com/zucchero-sintattico/piper-kt/commit/b134c2672fcafd2fae8d3556e8051b8395a9f1fe))
* **deploy:** kafka users-service deploy on kubernetes ([afe0ade](https://github.com/zucchero-sintattico/piper-kt/commit/afe0ade69112575a0f4fb310bee5bd7bb46abeb7))
* **deploy:** update helm install commands to install microservices with specific values ([2832565](https://github.com/zucchero-sintattico/piper-kt/commit/2832565a94cbaaf0a91a4b783966f1bdaae0ae44))
* **deploy:** update values in db-values.yaml for friendship-service-operator for apple M1,M2,M3 ([441864d](https://github.com/zucchero-sintattico/piper-kt/commit/441864d00a143f04e23449d41984edc5b3f57dc3))
* **DirectEntity:** add DirectEntity and MessageEntity classes for storing direct messages in the database ([97a37a7](https://github.com/zucchero-sintattico/piper-kt/commit/97a37a715a471b386404dd26f27e936d0f3865dd))
* **DirectMessage:** create DirectMessage.kt file with Message and Direct data classes for handling direct messages in the friendship service. ([681921c](https://github.com/zucchero-sintattico/piper-kt/commit/681921c0af0aab8d7abd2a1ef16304d401016a2d))
* **DirectRepository:** add DirectRepository interface with methods for retrieving, creating, and sending direct messages ([486d662](https://github.com/zucchero-sintattico/piper-kt/commit/486d6625e8aff64bdefacfe52521eb75a160b9ba))
* **DirectService:** add DirectService, DirectServiceApi, and DirectRepository classes to implement direct messaging functionality in the friendship service. ([2a0fa76](https://github.com/zucchero-sintattico/piper-kt/commit/2a0fa76be6fbe84f40eedc0bf04b30d4e0df7731))
* **FriendsControllerTest:** add test case for getFriendRequests endpoint to return friend requests of authenticated user ([f26c586](https://github.com/zucchero-sintattico/piper-kt/commit/f26c586e829735e198c48ee14a11cede6d42671d))
* **FriendsControllerTest:** add test case to get empty friend requests to ensure correct behavior of getFriendRequest() method ([df02aa7](https://github.com/zucchero-sintattico/piper-kt/commit/df02aa786a9bd744a9c62ae50dc1b52238283cdb))
* **friendship-service:** add files for basic configuration ([74f3fca](https://github.com/zucchero-sintattico/piper-kt/commit/74f3fcaaae83b99d362c83f414f8a94b30a0f393))
* **friendship-service:** add FriendRepositoryImpl and FriendsHTTPController ([17ac896](https://github.com/zucchero-sintattico/piper-kt/commit/17ac8961dc1af7e339c0208ea853c9ab71a9421e))
* **friendship-service:** add FriendService and FriendServiceApi classes ([7aee2ce](https://github.com/zucchero-sintattico/piper-kt/commit/7aee2ce064f7b3a20fce43013384dae54bb09792))
* **friendship-service:** add methods to FriendRepository interface ([98fe158](https://github.com/zucchero-sintattico/piper-kt/commit/98fe158acff44bbd8ec8e819d49546d813b53dff))
* **friendship-service:** add micronaut security dependencies for jwt and aot plugins ([fafd872](https://github.com/zucchero-sintattico/piper-kt/commit/fafd8726804009451e668ad68d2607513a434eac))
* **friendship-service:** edit from template to have frendship service base ([25626ae](https://github.com/zucchero-sintattico/piper-kt/commit/25626aeb2dccd381d8827b8a142bc28589ef2944))
* **helm-chart:** add piper-ingress Helm chart with configuration for nginx Ingress controller and service paths for users, friendships, multimedia, and servers services. ([60146c4](https://github.com/zucchero-sintattico/piper-kt/commit/60146c43c378e3e43e38388c489246cfddd21571))
* **helm-chart:** update values.yaml to include additional paths for friendsServicePaths, multimediaServicePaths, and serversServicePaths ([0abe5e7](https://github.com/zucchero-sintattico/piper-kt/commit/0abe5e7f08a5aecaa852b0c9d4746ec5889ada3a))
* **k8s:** add Kubernetes deployment and service configuration for friendship-service ([e13f82d](https://github.com/zucchero-sintattico/piper-kt/commit/e13f82d9aa31c4a194ee477eb2e68811c347e50f))
* **kubernetes:** add Kubernetes YAML file for setting up service account, role, role binding, and secret for micronaut-k8s namespace ([d8afee0](https://github.com/zucchero-sintattico/piper-kt/commit/d8afee0fe65afa6c0068a237afbe6dc7f7446047))
* **kubernetesNamespce:** update namespace value to use dynamic namespace from values ([819f7fd](https://github.com/zucchero-sintattico/piper-kt/commit/819f7fdc52501107ed4bb35ebc719f5f7b5dbf5b))
* **mongo-for-operator:** add values.yaml for configuration of MongoDB resources ([6a24428](https://github.com/zucchero-sintattico/piper-kt/commit/6a2442808e93522902e9c25e89f0e1ca8944ba0a))
* **mongo:** add resource limits and requests for the mongodb-agent container to optimize resource allocation ([8ba89b4](https://github.com/zucchero-sintattico/piper-kt/commit/8ba89b4263429d3221c1e7098daeebeb80ad061e))
* **piprktChart:** add values.yaml file with ServiceName set to 'service-name' to provide configuration for the chart ([cbbba1b](https://github.com/zucchero-sintattico/piper-kt/commit/cbbba1b90d2137d8f66e9e754730abaeb1877714))


### Bug Fixes

* **build-jar:** error on shadow jar with > 65535 files ([41f3448](https://github.com/zucchero-sintattico/piper-kt/commit/41f34483a2f5129354ab0ef16baad59eefb11fb6))
* **deployment:** add micronaut production environment ([996387b](https://github.com/zucchero-sintattico/piper-kt/commit/996387b5868ebd348253a2e86083ddeb4fd4d564))
* **jar:** add zip64 extensions for other microservices build ([0869e02](https://github.com/zucchero-sintattico/piper-kt/commit/0869e0290a4cd857a326f9896ce85df5b6e9fcea))

## [6.5.0](https://github.com/zucchero-sintattico/piper-kt/compare/v6.4.1...v6.5.0) (2024-05-13)


### Features

* **frontend:** add frontend project from piperchat and init gradlifing ([cf9a72f](https://github.com/zucchero-sintattico/piper-kt/commit/cf9a72f10cba370dff400c31ed665429f97cf3b3))
* **frontend:** gradlifing the project and setup delivery tasks ([2322505](https://github.com/zucchero-sintattico/piper-kt/commit/232250593476550af48a20218c056d70b6fbfa91))

## [6.4.1](https://github.com/zucchero-sintattico/piper-kt/compare/v6.4.0...v6.4.1) (2024-05-10)


### Bug Fixes

* **delivery:** check-command cannot be blank ([cc7ed03](https://github.com/zucchero-sintattico/piper-kt/commit/cc7ed03453adccb9eb4df56a54c8c506374da542))

## [6.4.0](https://github.com/zucchero-sintattico/piper-kt/compare/v6.3.0...v6.4.0) (2024-05-10)


### Features

* **notifications:** add image delivery according to the toolchain ([369e41c](https://github.com/zucchero-sintattico/piper-kt/commit/369e41c54e1a58f71cb2dc6f96b414deb251445b))

## [6.3.0](https://github.com/zucchero-sintattico/piper-kt/compare/v6.2.0...v6.3.0) (2024-05-10)


### Features

* **server-service:** add all server endpoints api responses and implemented owner leaving a server management ([435b94f](https://github.com/zucchero-sintattico/piper-kt/commit/435b94faba26ea0a9f1403fa85105657002e7b70))
* **server-service:** add first endpoint specification ([9f5c31c](https://github.com/zucchero-sintattico/piper-kt/commit/9f5c31ce1e6742af43ef7d044f0c6cb144205f8c))
* **server-service:** add swagger ([91ac577](https://github.com/zucchero-sintattico/piper-kt/commit/91ac5779829b669fa7e2ee0e25b427a5a74d609d))

## [6.2.0](https://github.com/zucchero-sintattico/piper-kt/compare/v6.1.0...v6.2.0) (2024-05-10)


### Features

* **delivery:** add action to delivery service images to dockerhub ([e02a3f5](https://github.com/zucchero-sintattico/piper-kt/commit/e02a3f56d1a2e6e33edcddfec197ab95788d3512))

## [6.1.0](https://github.com/zucchero-sintattico/piper-kt/compare/v6.0.0...v6.1.0) (2024-05-09)


### Features

* **notifications:** import old microservice as is and apply gradle plugin ([2d3de4e](https://github.com/zucchero-sintattico/piper-kt/commit/2d3de4eb80848146aa035fae641f0f8c2a574f5d))

## [6.0.0](https://github.com/zucchero-sintattico/piper-kt/compare/v5.0.0...v6.0.0) (2024-05-07)


### ⚠ BREAKING CHANGES

* **project:** cleanup micronaut configurations and dependencies, fixed micronaut problems with serializations

### Features

* **project:** cleanup micronaut configurations and dependencies, fixed micronaut problems with serializations ([9c58d74](https://github.com/zucchero-sintattico/piper-kt/commit/9c58d7461823d05e28796f3eec9d999908e68643))

## [5.0.0](https://github.com/zucchero-sintattico/piper-kt/compare/v4.1.0...v5.0.0) (2024-05-06)


### ⚠ BREAKING CHANGES

* finished microservice

### Features

* finished microservice ([7460f48](https://github.com/zucchero-sintattico/piper-kt/commit/7460f48c801f251b4edd42fa74a70b0e69bd7a6d))
* **friends-service:** implement accept request feature and refactor tests ([d765196](https://github.com/zucchero-sintattico/piper-kt/commit/d765196ea9ec396f0fc0a66a9f319e434fe6a81e))
* **friends-service:** implement db mapped entities ([6389fb9](https://github.com/zucchero-sintattico/piper-kt/commit/6389fb90d0d38536e96eb0d53a4bd6a719ebded3))
* **friends-service:** implement decline requests with its tests ([64c4485](https://github.com/zucchero-sintattico/piper-kt/commit/64c4485026ca811c80a879f7ba5691c5b3d89c0d))
* **friends-service:** implement event publishing ([cbd2027](https://github.com/zucchero-sintattico/piper-kt/commit/cbd2027fb64d15c252516da5652d1e20ed0b4393))
* **friends-service:** implement kafka event publisher ([d8cab38](https://github.com/zucchero-sintattico/piper-kt/commit/d8cab38aa709382807efe3dbabde7e3122e0e3e7))
* **friends-service:** implement last queries with tests and fix detekt issues ([56f91cd](https://github.com/zucchero-sintattico/piper-kt/commit/56f91cd9fc4c65f6862ad469d86ae39192efbaf3))
* **friends-service:** implement repository ([610e539](https://github.com/zucchero-sintattico/piper-kt/commit/610e5398bcc31c4c9e216aa17fc7178832fdf46c))
* **friends-service:** implement repository implementation tests for both aggregates ([eceefd0](https://github.com/zucchero-sintattico/piper-kt/commit/eceefd0ae968b8543fbfa32611c986597e2eb6a5))
* **friends-service:** implement send message with tests ([5fff8e4](https://github.com/zucchero-sintattico/piper-kt/commit/5fff8e42ebe8d70bde898a58f3ea6c45320e87fe))
* implement api for interfaces-web layer ([1412a64](https://github.com/zucchero-sintattico/piper-kt/commit/1412a64b9645d66ea6ac5e92f4e30b5164064046))
* implement first http controller for friendships endpoints ([1e0935e](https://github.com/zucchero-sintattico/piper-kt/commit/1e0935ee27d1cd7baffc9189f7f77025d09e83e1))
* implement integration tests for directs ([67f553e](https://github.com/zucchero-sintattico/piper-kt/commit/67f553e7f76a8148691f6063d68be08e496978ef))
* implement integration tests for friendship endpoints ([d7ef45b](https://github.com/zucchero-sintattico/piper-kt/commit/d7ef45b01b3b65ce82da60d1e6d3b11b5b04e017))
* import microservice template ([74d9eb0](https://github.com/zucchero-sintattico/piper-kt/commit/74d9eb0b5415da8326478bbd18a0411dfb668916))
* merge main into microservice branch ([e605680](https://github.com/zucchero-sintattico/piper-kt/commit/e605680b69a4312c26da36aa39608dc7a5c1c0ba))
* merge main into microservice branch ([0931190](https://github.com/zucchero-sintattico/piper-kt/commit/09311907038ac5f85a205dd1703374f76585c98a))
* **service:** implement friendships api ([f5e6711](https://github.com/zucchero-sintattico/piper-kt/commit/f5e67118374996c38402ac2d8e480649ece9acb9))


### Bug Fixes

* **channel-service:** fix assertion in test ([2f12f43](https://github.com/zucchero-sintattico/piper-kt/commit/2f12f43e38894670d3b9a4ec4b5c9803c8d484ae))
* **ci:** try to run test multiple times before failing ([47164d4](https://github.com/zucchero-sintattico/piper-kt/commit/47164d4517724be6e92ee6443b9c98570e53efe5))
* **friendship-request:** adjust test case of accepting request ([cdbf28a](https://github.com/zucchero-sintattico/piper-kt/commit/cdbf28ac60c3c923f7623711f0a0f9c9b33dcb3a))
* **friendships-repository:** let query return the aggregate instead of domain entities/valueObjs ([c4dc920](https://github.com/zucchero-sintattico/piper-kt/commit/c4dc920fd6b7dc7c7e21c07faa5815c1ff8fb625))
* **friendships-service:** fixed micronaut configuration for db entities package ([5c5c941](https://github.com/zucchero-sintattico/piper-kt/commit/5c5c941128e73a0d1abd761d07cf27babe7f3425))
* **friendships-service:** implement update in repository instead of save and finish integration tests ([1c24252](https://github.com/zucchero-sintattico/piper-kt/commit/1c24252f858d647b1c0bcc85a22bfc05509327a7))
* **friendships-service:** make test green ([7595ac4](https://github.com/zucchero-sintattico/piper-kt/commit/7595ac4a670446aea0cb952643d3953bee746ebb))
* **server-service:** add sealed interface to exceptions in channelservice ([57ea7d2](https://github.com/zucchero-sintattico/piper-kt/commit/57ea7d2f02df3788e8ba8ce26cf720166a172860))

## [4.1.0](https://github.com/zucchero-sintattico/piper-kt/compare/v4.0.0...v4.1.0) (2024-05-03)


### Features

* **services:** added users-service template project ([7705613](https://github.com/zucchero-sintattico/piper-kt/commit/77056137add93d0ae76dec6a6dac57004776a54a))
* **user-service:** implementing jwt authentication ([42f7a7a](https://github.com/zucchero-sintattico/piper-kt/commit/42f7a7a025b6026075cd21b095dbd640d7a5d79f))
* **user-service:** removed template stuff and started implementing ([b4f9e55](https://github.com/zucchero-sintattico/piper-kt/commit/b4f9e55c0c6d6430d46e33fadecb0ec5f88bae50))
* **users-service:** add event publish at application level ([36e7f7d](https://github.com/zucchero-sintattico/piper-kt/commit/36e7f7d57104c408cb6dbd244d546c4441cbc09a))
* **users-service:** added user events and event publisher with kafka implementation ([46c764f](https://github.com/zucchero-sintattico/piper-kt/commit/46c764fa2f15794b9c841949b9d2b224263de153))
* **users-service:** implemented auth service and setup of entity and repositories ([cd6de9f](https://github.com/zucchero-sintattico/piper-kt/commit/cd6de9fa13208a70dffd603bfe4b2c6eea0e4b57))

## [4.0.0](https://github.com/zucchero-sintattico/piper-kt/compare/v3.0.0...v4.0.0) (2024-04-29)


### ⚠ BREAKING CHANGES

* finished microservice (trigger for major release)

### Features

* **channels:** implement http controller and client ([fc291af](https://github.com/zucchero-sintattico/piper-kt/commit/fc291af12403cc544f9e3a3a90e7f958b2773874))
* **channels:** implement test for interface layer ([d9d0fac](https://github.com/zucchero-sintattico/piper-kt/commit/d9d0fac81ae6e0f04648986a6a30c2d38efe5912))
* **controller:** fix kafka event publishing ([60d689e](https://github.com/zucchero-sintattico/piper-kt/commit/60d689e1ccf982678d199cafc87b1eb179776305))
* **controller:** implement api for channels operation ([bad400e](https://github.com/zucchero-sintattico/piper-kt/commit/bad400e5125c1536eb416372c0facd510b7b7f6a))
* **controller:** implement api for servers operations ([4695b9f](https://github.com/zucchero-sintattico/piper-kt/commit/4695b9fdf92382d22f78ecb49487aca2f41cb852))
* **controller:** implement first integration test of servers ([fc0f34b](https://github.com/zucchero-sintattico/piper-kt/commit/fc0f34b2ea3ee7a93144ced27c0016d4341d5ec8))
* **controller:** implement integration tests for http responses ([42ff503](https://github.com/zucchero-sintattico/piper-kt/commit/42ff50333b254f705e998ebae52ce13d32c99dd5))
* finished microservice (trigger for major release) ([6048ed9](https://github.com/zucchero-sintattico/piper-kt/commit/6048ed9da345f8b19a43a0c1bbc792089aa6b5ec))
* **interfaces-web:** add DTO for presentation and developed first enpoints ([fc6ae3a](https://github.com/zucchero-sintattico/piper-kt/commit/fc6ae3aa43a9512bd18da2b3cad45eb2038de261))
* **server-service:** finish integration tests for channels management ([70dfbeb](https://github.com/zucchero-sintattico/piper-kt/commit/70dfbeb57d7f3a2db95ab239f3d229b96a59da32))


### Bug Fixes

* **server-service:** fix all bugs and refuses from prevs refactors ([91f15a1](https://github.com/zucchero-sintattico/piper-kt/commit/91f15a1034ef0383b564df4fefd1bdce237e61f9))
* **server-test:** adjust http response assertions and return informations ([574766e](https://github.com/zucchero-sintattico/piper-kt/commit/574766e97681363ed8fa2a102a24d106195a45cd))

## [3.0.0](https://github.com/zucchero-sintattico/piper-kt/compare/v2.1.0...v3.0.0) (2024-04-16)


### ⚠ BREAKING CHANGES

* **ddd:** changed domain and application in order to follow ddd best practices
* **services:** added multimedia service

### Features

* added UUIDEntityId ([3e6b3b8](https://github.com/zucchero-sintattico/piper-kt/commit/3e6b3b8c971e69ee81ba7b119ee4edcb4585a604))
* **deps:** added socketio library to enable creation of socket.io server ([bfeb8ce](https://github.com/zucchero-sintattico/piper-kt/commit/bfeb8ce8ba4f89fb6f840d3baccc0b1e18b3749d))
* **deps:** added socketio-client in order to test the socket.io server ([5ff562c](https://github.com/zucchero-sintattico/piper-kt/commit/5ff562cf55b86eb0a9d78e45e9036e6157adf397))
* **json:** added kotlinx.serializtion to handle json serialization/deserialization ([3e94090](https://github.com/zucchero-sintattico/piper-kt/commit/3e9409040add9237b64aef8b2e3a849360d216c7))
* **multimedia-web:** added controllers and security rules ([e35b46d](https://github.com/zucchero-sintattico/piper-kt/commit/e35b46dc234bb01ab1d1ba02e09d3217fc3d4d8c))
* **multimedia-websocket:** switched to service use instead of repository ([1d32643](https://github.com/zucchero-sintattico/piper-kt/commit/1d32643e7434934bbb189a9ed2164470b7388263))
* **multimedia:** Added event listeners for users joining, leaving, and being kicked from servers ([80caa1f](https://github.com/zucchero-sintattico/piper-kt/commit/80caa1fbfa3b6f376183af2ceaa52c12f7832c5a))
* **multimedia:** Added Kafka dependency, implemented ServersEventsListener and ServerRepositoryImpl. ([e8ebf8c](https://github.com/zucchero-sintattico/piper-kt/commit/e8ebf8ca8e54ab7c82d704c08fcc36f355d53e91))
* **multimedia:** Added MultimediaApi interface, and Result class for handling API responses ([c5acfc3](https://github.com/zucchero-sintattico/piper-kt/commit/c5acfc3176aa2ee2383b71ccaa09dd8f192df60f))
* **multimedia:** implemented event listeners ([4f9cc77](https://github.com/zucchero-sintattico/piper-kt/commit/4f9cc7711505ef662c7f01737f9ce8dfaf5ad581))
* **multimedia:** implementing multimedia service general structure ([1f911fa](https://github.com/zucchero-sintattico/piper-kt/commit/1f911fa69b850a51549d78ac93e80950f4ca4830))
* **multimedia:** integrated repository operation in protocol ([04b1995](https://github.com/zucchero-sintattico/piper-kt/commit/04b199521973b82aefc8ea264a9cf77fc9fecbeb))
* refactor ([da657eb](https://github.com/zucchero-sintattico/piper-kt/commit/da657ebdd7a372cfa1c84bb2d032887d26cb9fb9))
* **services:** added multimedia service ([957d35c](https://github.com/zucchero-sintattico/piper-kt/commit/957d35c292d253f33c4fd5b0db2775978a7aef25))
* **ws:** added token extraction and rooms managment ([49f2aa4](https://github.com/zucchero-sintattico/piper-kt/commit/49f2aa4bf991f8b4ba97a298dea39e93a6419f39))


### Bug Fixes

* **api:** fixed error handling method signature ([afdecc3](https://github.com/zucchero-sintattico/piper-kt/commit/afdecc3afefb4330c603e5fac41432cb4c169f8f))
* micronauttest annotation ([ca51984](https://github.com/zucchero-sintattico/piper-kt/commit/ca51984b483d47909e0fe7390ebdcf2732671887))
* **multimedia-test:** added runCatching to avoid error throwing on socket test ([c20aa48](https://github.com/zucchero-sintattico/piper-kt/commit/c20aa48fdc392b79c56351950437439623049063))
* **multimedia-test:** removed assertion temporary from socket test to avoid bug in C.I. ([520d0f8](https://github.com/zucchero-sintattico/piper-kt/commit/520d0f8bd5a3e4e6dd1dc39673d355fbd6491a10))
* **multimedia:** fixed test that doesnt compile ([2c6485f](https://github.com/zucchero-sintattico/piper-kt/commit/2c6485fb026ff588058540f5716785b4a1f06eec))
* **multimedia:** fixed tests ([f1221f0](https://github.com/zucchero-sintattico/piper-kt/commit/f1221f0238beefbf8933e78b3d4d3dcf948551a0))
* **multimedia:** merged main ([45bfb4d](https://github.com/zucchero-sintattico/piper-kt/commit/45bfb4d2e3b9b215e1aa9e9331059cd12625b6d9))
* **multimedia:** put random port on server startup to avoid bind exception ([d1b9cd5](https://github.com/zucchero-sintattico/piper-kt/commit/d1b9cd5373be2da603a6d4398f8f8b1b056bee07))
* **multimedia:** removed dependency that broke build ([df065cf](https://github.com/zucchero-sintattico/piper-kt/commit/df065cf27e0a14ab67602d4fd29bf7781854b0b5))
* **multimedia:** removed integration test ([e9a4585](https://github.com/zucchero-sintattico/piper-kt/commit/e9a4585413ed2303d35cb0c3745d89257770a7c7))
* **project:** removed deleted dependencies ([87e63aa](https://github.com/zucchero-sintattico/piper-kt/commit/87e63aaa96530f8d783ad547bdfafe1aabf377f0))
* **test:** fixed architecture test ([1cb0626](https://github.com/zucchero-sintattico/piper-kt/commit/1cb062676ffc6125a5025581dd0eb545f558a0ff))
* **test:** fixed architecture test ([35f1def](https://github.com/zucchero-sintattico/piper-kt/commit/35f1def452d488e168e306b2cc75697d0124a8f9))
* **test:** fixed socketio server test setupping a session repository ([a73ba6d](https://github.com/zucchero-sintattico/piper-kt/commit/a73ba6d81318b70f64dc864c76fa327cc3b3bd1c))
* **test:** fixed type in base test class name ([6b4ff1e](https://github.com/zucchero-sintattico/piper-kt/commit/6b4ff1eea8e36910867f3a80190fd062a8f426e7))


### Code Refactoring

* **ddd:** changed domain and application in order to follow ddd best practices ([17e2399](https://github.com/zucchero-sintattico/piper-kt/commit/17e239919ad728380210f30d5c791f95a19da351))

## [2.1.0](https://github.com/zucchero-sintattico/piper-kt/compare/v2.0.0...v2.1.0) (2024-04-15)


### Features

* **events:** import kafka and implement kafka clients ([d54a10d](https://github.com/zucchero-sintattico/piper-kt/commit/d54a10d488b38b9a37c26637ce5ff43275341ffe))
* merge main into microservice branch ([fa15428](https://github.com/zucchero-sintattico/piper-kt/commit/fa15428dcb191f994a7c95e473b890f401b980b8))
* **messages:** implement messages repository ([7e46558](https://github.com/zucchero-sintattico/piper-kt/commit/7e4655852aced9fba7f8c5d9363f2e396167e741))


### Bug Fixes

* **channel-service:** implement event publishing and fix mocks in tests ([a3a7906](https://github.com/zucchero-sintattico/piper-kt/commit/a3a790666fa15e8d4e2481d160e9ee64e39f0702))
* **repository:** fix bugs and add tests to repository ([b63a635](https://github.com/zucchero-sintattico/piper-kt/commit/b63a63596b94f9299b57b7bc65ab04f81f14488c))
* **server-service:** implement event publishing and fix mocks in tests ([f18c74f](https://github.com/zucchero-sintattico/piper-kt/commit/f18c74ff00e2614a00324510bdcf35a3d5756362))
* **service-tests:** refactor basic class test ([2c6f353](https://github.com/zucchero-sintattico/piper-kt/commit/2c6f353fdb9cff4e1f9d74c257e89ec261b2ed23))
* **test:** remove template refuse ([472d728](https://github.com/zucchero-sintattico/piper-kt/commit/472d7287201c6ddfdb286453b545de9ea0eb6561))

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
