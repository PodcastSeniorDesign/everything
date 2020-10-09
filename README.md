# Senior Design Project (Podcast App)
![Splash Screen](https://i.imgur.com/c7kzQjY.png)
![Player/Main](https://i.imgur.com/dp8Bi9p.png)
### Description

*coming soon*

### General info

#### Activity Flow:

**Start:** Splash -> Main (-> Login (-> Register -> Onboarding))

*the activities in the parentheses are optional

- Font
  - Raleway
- Colors
  - <https://material.io/resources/color/#!/?view.left=0&view.right=0&primary.color=7c4dff&secondary.color=424242>

### TODO

#### General

- [ ] Name
- [ ] Logo

  - [ ] Bigger and preferable a vector image
- [x] Color scheme

  - [x] Accent #FF4081
- [ ] Layout transitions and animations <https://developer.android.com/training/transitions>
- [ ] Password security. It's in plaintext in the app right now and being passed around to different methods
  - [ ] Code compile obfuscation
  - [ ] Constants go in Strings resource

#### App

- [ ] **Add Dagger for [dependency injection](https://developer.android.com/training/dependency-injection)**

  - [ ] This [Codelab](https://codelabs.developers.google.com/codelabs/android-dagger/#0) is pretty much a tutorial about how we want to create the app

- [ ] [ReactiveX](http://reactivex.io/) Java

  - [ ] Splash screen
    - [ ] Add text vector "waveform" to screen

- [ ] Login pages and flow

  - [ ] Login page (email/password, FB, and Google)

    - [ ] overlay/modal that says we recommend facebook to find friends

        https://developer.android.com/training/tv/playback/onboarding>
    - [x] **if registering, send email and password if possible to new activity**
    - [x] Move outlinedbox styling to new style
    - [ ] add forgot password option
  
  - [ ] Register view (name, email, password, picture) not a new activity but animations to move the forms around
      - [ ] animate changing text from "Sign in" to "Register"
      - [ ] move FB/Google buttons/register past bottom
      - [x] add name box to top 
      - [ ] animate change "sign in" button to "register"
      - [ ] password security requirements
      - [ ] create random picture for users without a profile picture eg. github
  
- [ ] Onboarding

  - [ ] Add favorite genres/topics or favorite podcasts
  - [ ] Add friends from Facebook/Google etc.
  
- [ ] Main Activity

  - [ ] Bottom Navigation Activity

    - [ ] Social Feed
    - [ ] My subscriptions
    - [ ] Explore
  - [ ] Top bar

    - [ ] Page name, profile picture, cast button
  - [ ] Floating Action Button

    - [ ] create post
  - [ ] 3 dots menu

    - [ ] Preferences
    - [ ] About
    - [ ] Help
    - [ ] Sign out
  
- [ ] Audio

  - [ ] Run as Foreground service

#### Firebase

- 

#### ML

- 

#### Ideas

- Waveform in the background the matches whatever audio is playing
  - navbar and notification bar change colors when playing podcast

### Development config

Android Virtual Device: 

* Develop with: **Pixel 3 XL w/ Android 10.0**
* Test minSDK and lowres screens with: **Nexus 4 w/ Android 6.0 **

Android Compile Version: **29**

Android MinSDK: **23**

### Authors

Rooshi Patidar

Rishab Chander

Hiep Nguyen

Kaila Prochaska

Grace Anconetani

Chris Classie
