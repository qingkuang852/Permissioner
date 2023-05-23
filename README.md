# Permissioner

[![](https://jitpack.io/v/qingkuang852/Permissioner.svg)](https://jitpack.io/#qingkuang852/Permissioner)


To get a Git project into your build:

Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.qingkuang852:Permissioner:Tag'
	}

Step 3. How to use in code

	Permissioner(arrayOf(android.Manifest.permission.READ_CALENDAR)).apply {
		    registerLauncher(this@MainActivity)
		}.launch { _, _ ->
		    Toast.makeText(this, "granted", Toast.LENGTH_SHORT).show()
		}
