# Version Control & Repository

The Version Control System (VCS) is a system that records changes to a file or set of files over time so that you can recall specific versions later.
It allows you to revert files back to a previous state, revert the entire project back to a previous state, compare changes over time, see who last modified something that might be causing a problem, who introduced an issue and when, and more.

The project uses **Git** as VCS.

The project is hosted on GitHub at [piper-kt](https://github.com/zucchero-sintattico/piper-kt).

## Version Control

### Semantic Versioning

The project uses [**Semantic Versioning**](https://semver.org) for software release versioning.

You can find releases on the [GitHub Releases](https://github.com/zucchero-sintattico/piper-kt/releases) section.

### Conventional Commits

The project uses [**Conventional Commits**](https://www.conventionalcommits.org) for commit messages.

#### Git Hooks

To enforce the use of Conventional Commits, the project uses a Git hook that checks the commit message format before allowing the commit.
To install automatically the Git hook, it's been used the [`org.danilopianini.gradle-pre-commit-git-hooks`](https://plugins.gradle.org/plugin/org.danilopianini.gradle-pre-commit-git-hooks) gradle plugin, as follow:

```kotlin
plugins {
    id("org.danilopianini.gradle-pre-commit-git-hooks") version "2.0.6"
}

gitHooks {
    commitMsg { conventionalCommits() }
    createHooks(overwriteExisting = true)
}
```

Every time someone tries to commit, the hook will check if the commit message follows the Conventional Commits format.

### Semantic Release

In order to automate the release process, the project uses [**Semantic Release**](https://semantic-release.gitbook.io/semantic-release) plugin to automatically determine the next version number,
generate the changelog, and publish the release, whenever a new commit is pushed to the _main_ branch.
Semantic Release uses the commit messages to determine the type of changes in the codebase.
Following formalized conventions for commit messages (Conventional Commits), semantic-release automatically determines the next semantic version number.

In this way the _main_ branch is the single source of truth for the project, and the release process is fully automated, ensuring that the releases are consistent and correctly versioned.

## Repository Management

### Branching Strategy

The project uses the branch `main` as default branch, where semantic versioning and release are applied.

Other main types of branches used in the project are:

- `feature/<feature-name>`: branch for a new feature development.
- `fix/<issue-name>`: branch for a bug fix.
- `merge/<integration-feature>`: branch for integrating an already developed feature into the main branch.

### Merging Strategy

The team mostly uses a `rebase` strategy for merging branches into the main.
This allow to keep the commit history clean and linear.
In case of conflicts during a PR, they should be resolved locally before merging the branch.

In addition, if many team members are working on the same branch, in order to enforce this strategy, git git is been configured to perform `pull with rebase` and `autostash` before pulling, as follow:

```bash
git config pull.rebase true
git config rebase.autoStash true
```

### Pull Requests

The project uses GitHub Pull Requests (PRs) to merge code changes from a branch into main.

This allows for code review, discussion, and automated checks before merging, in order to ensure the quality of the codebase and a general shared knowledge among the team.

Given the fact that the project is under heavy development, the team agreed to the fact that PRs are **not** mandatory for small changes, refinements e/o fixes, but it's recommended to use them to share knowledge and to have a better understanding of the codebase.
