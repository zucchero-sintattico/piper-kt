const config = {
    branches: ['main'],
    plugins: [
        // Looks for commits that are breaking changes, features, or fixes
        '@semantic-release/commit-analyzer',
        // Generates the release notes
        '@semantic-release/release-notes-generator',
        // Updates the version in package.json
        [
            '@semantic-release/git',
            {
                assets: ['CHANGELOG.md', 'package.json'],
                // This commit doesn't trigger ci/cd
                message: 'chore(release): ${nextRelease.version} [skip ci]\n\n${nextRelease.notes}'
            }
        ],
        // Publishes the release to GitHub
        '@semantic-release/github',
    ]
};

module.exports = config;