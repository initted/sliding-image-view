# sliding-image-view    [![](https://jitpack.io/v/initted/sliding-image-view.svg)](https://jitpack.io/#initted/sliding-image-view)
This is the repo for the custom image viwe which can be used populate multiple images with sliding animations


## For gradle.kts

Add it in your settings.gradle.kts at the end of repositories:

	dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url = uri("https://jitpack.io") }
		}
	}
 
Add the dependency in build.gradle.kts

	dependencies {
	        implementation("com.github.initted:sliding-image-view:v1.0.0")
	}
