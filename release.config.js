var publishCmd = `
IMAGE_NAME="danysk/docker-manjaro-texlive-ruby"
docker build -t "$IMAGE_NAME:\${nextRelease.version}"
docker push --all-tags "$IMAGE_NAME"
`
var config = require('semantic-release-preconfigured-conventional-commits');
config.plugins.push(
    [
        "@semantic-release/exec",
        {
            "publishCmd": publishCmd,
        }
    ],
    "@semantic-release/github",
    "@semantic-release/git",
)
module.exports = config