# Paper Plugin Template

## Project Setup

1. Generate the repository using this template
2. Clone the repository to your local machine
3. Open the project in IntelliJ IDEA
4. Replace `rootProject.name` in `settings.gradle.kts` with the name of your plugin
5. Replace the `Constants` in `build.gradle.kts` with appropriate values for your plugin
6. Sync the Gradle project

## Development

1. Create a package structure such as `dev.erudites.template` under `src/main/java/`
2. Create the plugin class `TemplatePlugin.java` in `src/main/java/dev/erudites/template/`
3. Make `TemplatePlugin` extend `JavaPlugin`
4. Define the main class and authors in `src/main/resources/plugin.yml`

## Gradle Tasks

- `./gradlew build` - Builds the plugin JAR file
- `./gradlew runServer` - Runs a local server for testing the plugin