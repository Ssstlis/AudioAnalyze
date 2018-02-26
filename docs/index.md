# Audio analyze 
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/2a5ff1dee06f40e8a7a38149f8888f80)](https://www.codacy.com/app/apostrof1995/AudioAnalyze?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Ssstlis/AudioAnalyze&amp;utm_campaign=Badge_Grade)
<br>This library provides convenient methods for music tags and album covers searching.<br>
It is platform independent and requires you to implement few interfaces from itself.<br>
The library uses its own data sorting and filtering methods for compatibility with Android.<br>
For quick start, you need to download the [latest release](https://github.com/Ssstlis/AudioAnalyze/releases/latest) and slf4j logger implementation for your platform.<br>

## All documentation
- [Quick start](QuickStart.md)
- [ProgressState class](ProgressState.md)
- [FingerPrint interface](FingerPrint.md)
- [ID3V2 class](ID3V2.md)
- [ ] [Samples](Samples.md)
- [Release notes](Release_notes.md)
- [Future tools](Future.md)

## Dependencies
- annotations-8.jar by JetBrains
- [Google GSON 2.8.2](https://github.com/google/gson/releases/)
- [okhttp 3.9.1](https://github.com/square/okhttp/releases/)
- [okio 1.13.0](https://github.com/square/okio/releases/)
- [slf4j 1.7.25](https://www.slf4j.org/dist/slf4j-1.7.25.zip)
- [MusicBrainzAndroid](https://github.com/Ssstlis/AudioAnalyze/releases/download/v1.0.0/MusicBrainzAndroid.jar)
- [JAudioTagger 2.2.3](https://github.com/Ssstlis/AudioAnalyze/releases/download/v1.0.0/jaudiotagger-2.2.3.jar)

## License
````
MIT License

Copyright (c) 2018 Ivan Aristov (Ssstlis/Fox)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
````