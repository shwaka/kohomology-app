Here are two applications which can be used as templates to use [shwaka/kohomology](https://github.com/shwaka/kohomology).

- `kohomology-cli`: Run the application in the command line. (recommended)
- `kohomology-browser`: Run the application in the browser.
- `kohomology-react`: Input a Sullivan algebra from the browser. (See [github pages](https://shwaka.github.io/kohomology-app/))

## Requirement
You need to install Java (JVM) from [Java | Oracle](https://www.java.com/en/) ([日本語ページ](https://www.java.com/ja/)).
The version of Java is not relevant, I believe, since the application uses `gradle`.

## Usage
You can run each application by the following way:

- Linux, Mac: Run `runner.sh` from terminal (or possibly click it?)
- Windows: Click `runner.bat` from Explorer
- Also you can use `gradle` directly by executing `./gradlew run` (or `gradlew.bat run`) in the command line, of course.

You can define your own chain complex and compute its cohomology by editing the script `src/main/kotlin/main.kt`.
I *strongly recommend* to use [IntelliJ IDEA](https://www.jetbrains.com/idea/), which is very helpful to write the script correctly.

## More examples
There are a lot of examples in tests in [shwaka/kohomology](https://github.com/shwaka/kohomology).
