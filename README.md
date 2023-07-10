# Versioning Project Demo - Java Client

The versioning project is a demo to a process of correctly versioning a project, and based on the working branch automatically setting the dependencies.

This project uses Java 17 and Gradle 8.1.1.

This repository demonstrates the Java client of a weather service.<br>
At the moment we don't do any versioning for the code.

## Usage

### Clone
First clone the project from GitHub: https://github.com/guyafe/versioning-client-java.git

### Run
Run the client using `gradle run`. It will run the client with the default parameters.

### Command Line Usage
Usage:

`health - Shows server health status`<br>
`weather <city> - return the weather for the next day in the given city`<br>
`exit - exits application`

## Gradle Usage

### Build

Using the command `gradle build` will create an executable jar artifact in the `build/libs` directory.<br>
Depending on the current branch:
* If the current branch is main, the artifact will be named _\<major\>_._\<minor\>_._\<patch\>_
* If the current branch is not main, the artifact will be named _\<major\>_._\<minor\>_._\<branch\>_-_SNAPSHOT_

### Publish
In order to publish this artifact, and have it available for other repositories, use the command `gradle publishToMavenLocal`.<br>
* If the current branch is main:
    1. The task will publish the artifact into local repository (usually in `~/.m2/repository`).
    2. Next, the task will check if the branch is clean (no uncommitted changes).. If so, it will bump the _minor_ version and commit the change with **[NO CI]** message.
* If the current branch is not main, the task will publish the artifact into local repository.
