# Pocket Dev - Mobile Coding Workspace

![Pocket Dev Logo](app/src/main/res/drawable/ic_splash.xml)

**Code Anywhere, Learn Everywhere**

Pocket Dev is a native Android coding application designed for students and beginners who want to learn programming on their mobile devices. With support for 8 programming languages, on-device code execution, and AI-powered assistance, Pocket Dev makes programming accessible to everyone.

## Features

### Multi-Language Code Editor
- **Syntax highlighting** for 8 languages:
  - Python
  - JavaScript
  - HTML
  - CSS
  - Java
  - C++
  - Kotlin
  - JSON
- **Intelligent autocomplete** for 7 languages
- Line numbers with optional highlighting
- Configurable tab size (2 or 4 spaces)
- Dark and light themes
- Mobile-optimized with pinch zoom and gesture support

### On-Device Code Execution
Execute code directly on your device without an internet connection:

- **Python** - Powered by Chaquopy (Python 3.11)
- **JavaScript** - Powered by Mozilla Rhino (ES6+ support)
- **HTML** - Rendered in Android WebView with full CSS and JavaScript support

### AI-Powered Features (Groq API)
Get help from AI to improve your coding:

- **Fix Bug** - Automatically detect and fix errors in your code
- **Explain Code** - Get beginner-friendly explanations of any code
- **Improve Code** - Receive suggestions for best practices and optimizations

*Note: AI features require a Groq API key. Get yours free at [console.groq.com](https://console.groq.com)*

### Project Management
- Create and save multiple projects
- Auto-save every 30 seconds (configurable)
- Search and filter projects
- Sort by name, date, or language
- Duplicate and rename projects
- Export and import code files

### Learning Resources
Built-in code examples library with 15+ examples:
- **5 Python examples** - From Hello World to Classes & Objects
- **5 JavaScript examples** - Variables to Fetch API
- **5 HTML examples** - Basic structure to JavaScript integration

## Installation

### Requirements
- Android 8.0 (API 26) or higher
- 100 MB free storage space
- Internet connection (only for AI features)

### From APK
1. Download the `PocketDev-v1.0.0.apk` file
2. Enable "Install from Unknown Sources" in your device settings
3. Open the APK file and tap "Install"
4. Launch Pocket Dev from your app drawer

### From Source
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/pocketdev.git
   ```
2. Open the project in Android Studio
3. Sync Gradle and build the project
4. Run on your device or emulator

## Getting Started

### First Launch
1. Complete the onboarding tutorial
2. Create your first project by tapping the "+" button
3. Select your preferred programming language
4. Start coding!

### Setting Up AI Features
1. Go to **Settings** → **AI Integration**
2. Tap "Groq API Key Help" to get your free API key
3. Enter your API key in the settings
4. Start using AI features from the editor menu

### Running Code
1. Write your code in the editor
2. Tap the **Run** button (play icon)
3. View output in the Console tab
4. For HTML, view the rendered output in the Preview tab

## Technical Architecture

### Platform
- **Language**: Kotlin
- **Minimum SDK**: Android 8.0 (API 26)
- **Target SDK**: Android 14 (API 34)
- **Architecture**: MVVM pattern

### Key Libraries
| Library | Purpose |
|---------|---------|
| Sora Editor | Code editor with syntax highlighting |
| Chaquopy | Python execution on Android |
| Mozilla Rhino | JavaScript execution |
| Retrofit | HTTP client for Groq API |
| Room | Local database for projects |
| Material Design 3 | Modern UI components |

### Execution Engines
```
[UI Layer]
    ↓
[ViewModel]
    ↓
[ExecutionManager]
    ↓              ↓              ↓
[PythonEngine] [JSEngine]  [HTMLEngine]
(Chaquopy)     (Rhino)     (WebView)
```

## Groq API Integration

Pocket Dev exclusively uses the **Groq API** for AI features.

### Configuration
- **Base URL**: `https://api.groq.com/openai/v1/`
- **Authentication**: Bearer token
- **Models**: 
  - Llama 3.3 70B (default)
  - Mixtral 8x7B

### Getting an API Key
1. Visit [console.groq.com](https://console.groq.com)
2. Create a free account
3. Generate an API key
4. Enter the key in Pocket Dev settings

### API Features
- **Fix Bug**: Sends code to Groq for analysis and correction
- **Explain Code**: Requests beginner-friendly explanations
- **Improve Code**: Gets optimization suggestions

## Project Structure

```
app/src/main/java/com/pocketdev/
├── data/
│   ├── local/
│   │   ├── converter/      # Room type converters
│   │   ├── dao/            # Data access objects
│   │   ├── database/       # Room database
│   │   ├── entity/         # Database entities
│   │   └── preferences/    # Settings preferences
│   ├── remote/
│   │   ├── GroqApiService.kt
│   │   └── RetrofitClient.kt
│   ├── repository/
│   │   └── AiRepository.kt
│   └── ExamplesLibrary.kt
├── execution/
│   ├── ExecutionManager.kt
│   └── engine/
│       ├── PythonExecutionEngine.kt
│       ├── JavaScriptExecutionEngine.kt
│       └── HtmlExecutionEngine.kt
├── model/
│   └── Project.kt
├── ui/
│   ├── editor/
│   │   └── EditorActivity.kt
│   ├── examples/
│   │   ├── ExamplesActivity.kt
│   │   └── ExamplesAdapter.kt
│   ├── onboarding/
│   │   └── OnboardingActivity.kt
│   ├── projects/
│   │   ├── ProjectsFragment.kt
│   │   └── ProjectsAdapter.kt
│   ├── settings/
│   │   ├── SettingsActivity.kt
│   │   └── SettingsFragment.kt
│   └── MainActivity.kt
└── PocketDevApplication.kt
```

## Customization

### Themes
Choose from three theme options:
- **Dark** (default) - Easy on the eyes for coding
- **Light** - Clean and bright
- **Auto** - Follows system theme

### Editor Settings
- **Font Size**: Small (12sp), Medium (14sp), Large (16sp)
- **Tab Size**: 2 or 4 spaces
- **Line Numbers**: Show/hide
- **Autocomplete**: Enable/disable

## Troubleshooting

### Code Won't Execute
- Check if the language is executable (Python, JavaScript, HTML)
- Ensure code doesn't have syntax errors
- Check for timeout (10 second limit)

### AI Features Not Working
- Verify your Groq API key is entered correctly
- Check internet connection
- Ensure API key starts with "gsk_"

### App Crashes
- Clear app data and cache
- Reinstall the app
- Check if device meets minimum requirements

### Storage Issues
- Export projects before clearing data
- Delete unused projects
- Clear console output regularly

## Known Limitations

1. **Python**: Limited to standard library (no pip install support)
2. **JavaScript**: No DOM access outside WebView
3. **Execution**: 10-second timeout for all languages
4. **AI**: Requires internet connection and valid API key

## Contributing

We welcome contributions! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- [Sora Editor](https://github.com/Rosemoe/sora-editor) - Powerful code editor for Android
- [Chaquopy](https://chaquo.com/chaquopy/) - Python SDK for Android
- [Mozilla Rhino](https://github.com/mozilla/rhino) - JavaScript engine in Java
- [Groq](https://groq.com) - Ultra-fast AI inference
- [Material Design 3](https://m3.material.io/) - Modern design system

## Support

For support, please:
- Open an issue on GitHub
- Email: support@pocketdev.app
- Join our Discord community

## Roadmap

- [ ] Code snippets library
- [ ] Multiple file tabs
- [ ] Code formatting/beautify
- [ ] Git integration
- [ ] Cloud sync
- [ ] Collaborative editing
- [ ] More language support (Rust, Go, etc.)

---

**Made with ❤️ for students and learners everywhere**

*Version 1.0.0 | Last Updated: 2024*
