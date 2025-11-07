allprojects {
    repositories {
        mavenLocal()
        maven {                                           // remove on RBN
            url = uri("https://mvn.geniyz.site/releases") // remove on RBN
            isAllowInsecureProtocol = true                // remove on RBN
        }                                                 // remove on RBN
        mavenCentral()
    }
}
