<h1 align="center">
<img width="50%" src="https://user-images.githubusercontent.com/72238126/206612101-5d421687-5db6-4a22-92cb-62a501743704.png"/>
</h1>

<p align="center">

<img src="https://img.shields.io/badge/Kotlin-232F3E?style=flat-square&logo=kotlin&logoColor=FFFFFF"/>
<img src="https://img.shields.io/badge/Android-232F3E?style=flat-square&logo=android&logoColor=FFFFFF"/>
<img src="https://img.shields.io/badge/Javascript-232F3E?style=flat-square&logo=javascript&logoColor=FFFFFF"/>
<img src="https://img.shields.io/badge/Node.js-232F3E?style=flat-square&logo=nodedotjs&logoColor=FFFFFF"/>
<img src="https://img.shields.io/badge/MongoDB-232F3E?style=flat-square&logo=mongodb&logoColor=FFFFFF"/>
<img src="https://img.shields.io/badge/AWS-232F3E?style=flat-square&logo=amazon-aws&logoColor=FFFFFF"/>

</p>
<p align="center">
"Zone Trainer is a mobile application that allows you to easily check and record your brain waves with EEG headset."
</p>

![image](https://user-images.githubusercontent.com/72238126/206612172-0b9823e4-0091-420c-a652-827d14b181a1.png)

<br>

# Download

Go to the [Releases](https://github.com/neurosky-internship/zonetrainer-android/releases/tag/v1.0.0) to download the installable APK file.

<br>

# Features

- Check your brain waves in real-time with EEG headset.
- Record screen with your appearance and brain waves together.
- Show dashboard of your recent brain waves data.

<br>

# Tech Stack

### Android

- 100% [Kotlin](https://kotlinlang.org/) based including Kotlin DSL for Gradle.
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- [Gradle Version Catalog](https://docs.gradle.org/current/userguide/platforms.html) for control version of dependencies.
- Jetpack
  - Lifecycle: Observe Android lifecycles and handle UI states upon the lifecycle changes.
  - ViewModel: Manages UI-related data holder and lifecycle aware.
  - [Hilt](https://dagger.dev/hilt/): for dependency injection.
  - [Compose](https://developer.android.com/jetpack/compose): for implementation of UI/UX.
- Architecture
  - MVVM Architecture (View - ViewModel - Model)
  - Unidirectional Data Flow Pattern
  - Repository Pattern
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit): Construct the REST APIs and exchange network data.
- Bluetooth Adapter for connection with MindWave EEG headset.
- Google Auth API with [OAuth2.0](https://developers.google.com/identity/protocols/oauth2)
- [HBRecorder](https://github.com/HBiSoft/HBRecorder) for record screen while training.

