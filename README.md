# github-trending-cli

Java CLI for fetching and displaying trending repositories from the GitHub API.

## Features

- time-range filtering: `day`, `week`, `month`, `year`
- configurable result limit (`--limit`)
- readable terminal output
- built-in `--help` / `-h`

## Requirements

- Java 17+
- Maven 3.9+

## Build

```bash
mvn clean package
```

After the build, an executable fat JAR is created:

`target/github-trending-cli-1.0-SNAPSHOT.jar`

## Run

```bash
java -jar target/github-trending-cli-1.0-SNAPSHOT.jar --duration week --limit 10
```

## Help

```bash
java -jar target/github-trending-cli-1.0-SNAPSHOT.jar --help
```

## Examples

```bash
java -jar target/github-trending-cli-1.0-SNAPSHOT.jar --duration day --limit 5
java -jar target/github-trending-cli-1.0-SNAPSHOT.jar --duration month --limit 20
java -jar target/github-trending-cli-1.0-SNAPSHOT.jar --duration year --limit 10
```

## GitHub API rate limit

For higher request limits, set a token:

```bash
$env:GITHUB_TOKEN="your_token"
java -jar target/github-trending-cli-1.0-SNAPSHOT.jar --duration week --limit 10
```
