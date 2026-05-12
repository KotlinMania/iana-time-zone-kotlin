# iana-time-zone-kotlin in Kotlin

[![GitHub link](https://img.shields.io/badge/GitHub-KotlinMania%2Fiana--time--zone--kotlin-blue.svg)](https://github.com/KotlinMania/iana-time-zone-kotlin)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.kotlinmania/iana-time-zone-kotlin)](https://central.sonatype.com/artifact/io.github.kotlinmania/iana-time-zone-kotlin)
[![Build status](https://img.shields.io/github/actions/workflow/status/KotlinMania/iana-time-zone-kotlin/ci.yml?branch=main)](https://github.com/KotlinMania/iana-time-zone-kotlin/actions)

This is a Kotlin Multiplatform line-by-line transliteration port of [`strawlab/iana-time-zone`](https://github.com/strawlab/iana-time-zone).

**Original Project:** This port is based on [`strawlab/iana-time-zone`](https://github.com/strawlab/iana-time-zone). All design credit and project intent belong to the upstream authors; this repository is a faithful port to Kotlin Multiplatform with no behavioural changes intended.

### Porting status

This is an **in-progress port**. The goal is feature parity with the upstream Rust crate while providing a native Kotlin Multiplatform API. Every Kotlin file carries a `// port-lint: source <path>` header naming its upstream Rust counterpart so the AST-distance tool can track provenance.

---

## Upstream README — `strawlab/iana-time-zone`

> The text below is reproduced and lightly edited from [`https://github.com/strawlab/iana-time-zone`](https://github.com/strawlab/iana-time-zone). It is the upstream project's own description and remains under the upstream authors' authorship; links have been rewritten to absolute upstream URLs so they continue to resolve from this repository.

## iana-time-zone - get the IANA time zone for the current system

[![Crates.io](https://img.shields.io/crates/v/iana-time-zone.svg)](https://crates.io/crates/iana-time-zone)
[![Documentation](https://docs.rs/iana-time-zone/badge.svg)](https://docs.rs/iana-time-zone/)
[![Crate License](https://img.shields.io/crates/l/iana-time-zone.svg)](https://crates.io/crates/iana-time-zone)
[![build](https://github.com/strawlab/iana-time-zone/actions/workflows/rust.yml/badge.svg)](https://github.com/strawlab/iana-time-zone/actions?query=branch%3Amain)

This small utility crate gets the IANA time zone for the current system. This is
also known as the [tz database], tzdata, the zoneinfo database, and the Olson
database.

[tz database]: https://en.wikipedia.org/wiki/Tz_database

Example:

```rust
// Get the current time zone as a string.
let tz_str = iana_time_zone::get_timezone()?;
println!("The current time zone is: {}", tz_str);
```

You can test this is working on your platform with:

```
cargo run --example get_timezone
```

## Minimum supported rust version policy

This crate has a minimum supported rust version (MSRV) of 1.62.0 for [Tier 1]
platforms.

[tier 1]: https://doc.rust-lang.org/1.62.0/rustc/platform-support.html

Updates to the MSRV are sometimes necessary due to the MSRV of dependencies.
MSRV updates will not be indicated as a breaking change to the semver version.

## License

Licensed under either of

- Apache License, Version 2.0 ([LICENSE-APACHE](https://github.com/strawlab/iana-time-zone/blob/HEAD/LICENSE-APACHE) or
  <http://www.apache.org/licenses/LICENSE-2.0>)
- MIT license ([LICENSE-MIT](https://github.com/strawlab/iana-time-zone/blob/HEAD/LICENSE-MIT) or
  <http://opensource.org/licenses/MIT>)

at your option.

Unless you explicitly state otherwise, any contribution intentionally submitted
for inclusion in the work by you, as defined in the Apache-2.0 license, shall be
dual licensed as above, without any additional terms or conditions.

---

## About this Kotlin port

### Installation

```kotlin
dependencies {
    implementation("io.github.kotlinmania:iana-time-zone-kotlin:0.1.0")
}
```

### Building

```bash
./gradlew build
./gradlew test
```

### Targets

- macOS arm64
- Linux x64
- Windows mingw-x64
- iOS arm64 / simulator-arm64 (Swift export + XCFramework)
- JS (browser + Node.js)
- Wasm-JS (browser + Node.js)
- Android (API 24+)

### Porting guidelines

See [AGENTS.md](AGENTS.md) and [CLAUDE.md](CLAUDE.md) for translator discipline, port-lint header convention, and Rust → Kotlin idiom mapping.

### License

This Kotlin port is distributed under the same MIT license as the upstream [`strawlab/iana-time-zone`](https://github.com/strawlab/iana-time-zone). See [LICENSE](LICENSE) (and any sibling `LICENSE-*` / `NOTICE` files mirrored from upstream) for the full text.

Original work copyrighted by the iana-time-zone authors.  
Kotlin port: Copyright (c) 2026 Sydney Renee and The Solace Project.

### Acknowledgments

Thanks to the [`strawlab/iana-time-zone`](https://github.com/strawlab/iana-time-zone) maintainers and contributors for the original Rust implementation. This port reproduces their work in Kotlin Multiplatform; bug reports about upstream design or behavior should go to the upstream repository.
