# VIScon 2024 - Ubique Android Map Workshop

This repository contains the source code and [presentation slides](Viscon2024_Ubique_Workshop_Android.pdf) for the "Android Map Workshop" organized by [Ubique](https://www.ubique.ch) at [VIScon 2024](https://viscon.vis.ethz.ch/2024).

The goal of this workshop is to create an Android app using the open-source map SDK [Open Mobile Maps](https://openmobilemaps.io/). In the app, users will be able to place markers with descriptions and custom icons on a simplified vector map based on the [Swisstopo Light Base Map](https://www.swisstopo.admin.ch/en/web-maps-light-base-map). If enabled, their location will also be shown on the map.

### How to use this repository

The starting point for the implementation is the code skeleton provided in the latest commit. All locations in the source code where adjustments or additions are needed are marked with tags and include comments explaining what to do. The order of these steps is detailed in the [slides](Viscon2024_Ubique_Workshop_Android.pdf), which also include instructions and code examples.

Additionally, the commit history provides a step-by-step guide to the final solution: each commit is marked with the tag of the corresponding task. The initial (oldest) commit contains the complete and working project.

### Prerequisites

This project shares the device requirements of Open Mobile Maps, which are as follows:

- Android 8.0 or higher
- OpenGL ES 3.2 support

Please note that the Android Studio emulator currently only supports OpenGL ES 3.1.
