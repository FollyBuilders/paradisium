# Burning Man 2022 - Paradisium
![](docs/assets/paradisium.jpg)
![image](https://user-images.githubusercontent.com/4576814/180364483-1789628d-f849-4a53-93ef-6188105ae73f.png)


# How To 

## Bootstrap
LXStudio is built on top of [Processing](https://processing.org/) which uses [jogl](https://jogamp.org/jogl/www/) under the hood. As such, you need to run a small script which will symlink the native libraries into the appropriate location

```sh
$ bash bootstrap.sh
```

## How To

### Run LXStudio
```sh
$ ./gradlew run
```

### Run Tests
```sh
$ ./gradlew test
```

### Run auto linter
```sh
$ ./gradlew spotlessApply
```

# Special Thanks
- John Parts Taylor([partsdept](https://github.com/partsdept)): For the legwork getting the model generation and initial lighting design worked out!
- Mark Slee([mcslee](https://github.com/mcslee)): Creator of LX* and for all the help explaing it over the years.
