pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()

    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://developer.huawei.com/repo/")
        maven("https://developer.hihonor.com/repo")
    }
}

rootProject.name = "copy_device_info"
include(":app")