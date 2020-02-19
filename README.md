# Welcome to Material Phone Widget!

[![](https://jitpack.io/v/thushcapone/material_phone_widget.svg)](https://jitpack.io/#thushcapone/material_phone_widget) 

MaterialPhoneWidget is your phone widget compatible with Android Architecture Component 

### Demo
![gif of MaterialPhoneWidget](https://raw.githubusercontent.com/thushcapone/material_phone_widget/master/screenshots/demo-material-phone.gif)


## Adding to project

### Gradle
Add below code to your **root** `build.gradle` file (if you have multiple modules and only one of them require `MaterialPhoneWidget`, add the `jitpack` url only in that module's `build.gradle`).
```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
And add the following dependency code to your **module**'s `build.gradle` file.
```gradle
dependencies {
    implementation "com.github.thushcapone:material_phone_widget:${latest-version}"
}
```

## Usage
```xml
<com.thushcapone.material_phone_widget.PhoneWidget  
  android:layout_width="match_parent"  
  android:layout_height="wrap_content"  
  android:layout_gravity="center"  
  app:hint="Enter your phone number"  
  app:defaultCountry="US"  
  />
```

## Usage with MVVM + DataBinding
```xml
<com.thushcapone.material_phone_widget.PhoneWidget  
  android:id="@+id/phone_number_login"  
  android:layout_width="match_parent"  
  android:layout_height="wrap_content"  
  android:layout_gravity="center"  
  app:hint="Enter your phone number"
  app:phoneNumber="@={viewModel.phoneNumber}"  
  app:onValidPhoneListener="@{viewModel::onValidPhoneTyped}"  
  bind:countryCodeListener="@{viewModel::onCountryCodeSelected}"  
  />
```

### Customization

Table below describes the properties available to customize the AwesomeNavigation. Some options are applicable to only certain styles


| Property Name          | Format    | Description |
|------------------------|-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| hint            | reference     | Set the string resource used as the default hint                             |
| defaultCountry            | string | Set the default country code to show, expecting the country short code (E.g. `'US'`, `'FR'`) |
| phoneNumber           | binding-value | Will receive the value of the phoneNumber typed  |
| countryCodeListener                | binding-function | Will be called when the country picker's value changed |

# License
```
Copyright (c) 2019. T.C.  
All Rights Reserved  
  
This product is protected by copyright and distributed under  
licenses restricting copying, distribution and decompilation.
```