Here are two applications which can be used as templates to use [shwaka/kohomology](https://github.com/shwaka/kohomology).

- `kohomology-cli`: Run the application in the command line. (recommended)
- `kohomology-browser`: Run the application in the browser.
- `kohomology-react`: Input a Sullivan algebra from the browser. (See [github pages](https://shwaka.github.io/kohomology-app/))

## Requirement
You need to install Java Development Kit (JDK).
Version 8 (=1.8) is recommended.

- [Windows] Install from [Amazon Corretto](https://docs.aws.amazon.com/corretto/latest/corretto-8-ug/downloads-list.html). Usually you will choose [amazon-corretto-8-x64-windows-jdk.msi](https://corretto.aws/downloads/latest/amazon-corretto-8-x64-windows-jdk.msi)
- [Mac, Linux] First, install [sdkman](https://sdkman.io/). Then run `sdk install java 8.292.10.1-amzn`.

## Usage
You can run each application by the following way:

- Linux, Mac: Run `runner.sh` from terminal (or possibly click it?)
- Windows: Click `runner.bat` from Explorer
- Also you can use `gradle` directly by executing `./gradlew run` (or `gradlew.bat run`) in the command line, of course.

You can define your own chain complex and compute its cohomology by editing the script `src/main/kotlin/main.kt`.
I *strongly recommend* to use [IntelliJ IDEA](https://www.jetbrains.com/idea/), which is very helpful to write the script correctly.

## More examples
There are a lot of examples in tests in [shwaka/kohomology](https://github.com/shwaka/kohomology).
