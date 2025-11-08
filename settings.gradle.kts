rootProject.name = "site.geniyz.ofp"

pluginManagement {
    repositories {
        mavenLocal()

        maven {                                           // remove on RBN
            url = uri("https://mvn.geniyz.site/releases") // remove on RBN
            isAllowInsecureProtocol = true                // remove on RBN
        }                                                 // remove on RBN

        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenLocal()

        maven {                                           // remove on RBN
            url = uri("https://mvn.geniyz.site/releases") // remove on RBN
            isAllowInsecureProtocol = true                // remove on RBN
        }                                                 // remove on RBN

        mavenCentral()
    }
}

include(":app")
include(":rules")
include(":repo-fs")
include(":repo-db")
