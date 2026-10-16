# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 16/17 (94.1%)
- **Function parity:** 138/138 matched (target 171) — 100.0%
- **Class/type parity:** 12/12 matched (target 52) — 100.0%
- **Combined symbol parity:** 150/150 matched (target 223) — 100.0%
- **Average inline-code cosine:** 0.52 (function body across 16 matched files)
- **Average documentation cosine:** 0.13 (doc text across 16 matched files)
- **Cheat-zeroed Files:** 0
- **Critical Issues:** 9 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. windows_bindings

- **Target:** `ianatimezone.WindowsBindings`
- **Similarity:** 0.50
- **Dependents:** 0
- **Priority Score:** 10705.0
- **Functions:** 101/101 matched (target 110)
- **Missing functions:** _none_
- **Types:** 6/6 matched (target 19)
- **Missing types:** _none_

### 2. tz_linux

- **Target:** `ianatimezone.TzLinux`
- **Similarity:** 0.57
- **Dependents:** 0
- **Priority Score:** 1104.3
- **Functions:** 8/8 matched (target 12)
- **Missing functions:** _none_
- **Types:** 3/3 matched (target 6)
- **Missing types:** _none_
- **Tests:** 1/1 matched

### 3. tz_darwin

- **Target:** `ianatimezone.TzDarwin`
- **Similarity:** 0.68
- **Dependents:** 0
- **Priority Score:** 903.2
- **Functions:** 7/7 matched (target 10)
- **Missing functions:** _none_
- **Types:** 2/2 matched (target 4)
- **Missing types:** _none_

### 4. ffi_utils

- **Target:** `ianatimezone.FfiUtils`
- **Similarity:** 0.65
- **Dependents:** 0
- **Priority Score:** 603.5
- **Functions:** 6/6 matched (target 7)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 4)
- **Missing types:** _none_
- **Tests:** 4/4 matched

### 5. tz_ohos

- **Target:** `ianatimezone.TzOhos`
- **Similarity:** 0.73
- **Dependents:** 0
- **Priority Score:** 302.7
- **Functions:** 2/2 matched
- **Missing functions:** _none_
- **Types:** 1/1 matched (target 2)
- **Missing types:** _none_

### 6. tz_wasm32_unknown

- **Target:** `ianatimezone.TzWasm32Unknown`
- **Similarity:** 0.63
- **Dependents:** 0
- **Priority Score:** 203.7
- **Functions:** 2/2 matched (target 8)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 3)
- **Missing types:** _none_
- **Tests:** 1/1 matched

### 7. tz_windows

- **Target:** `ianatimezone.TzWindows`
- **Similarity:** 0.64
- **Dependents:** 0
- **Priority Score:** 203.6
- **Functions:** 2/2 matched (target 3)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 2)
- **Missing types:** _none_

### 8. tz_android

- **Target:** `ianatimezone.TzAndroid`
- **Similarity:** 0.75
- **Dependents:** 0
- **Priority Score:** 202.5
- **Functions:** 2/2 matched (target 6)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 3)
- **Missing types:** _none_

### 9. tz_wasm32_wasi

- **Target:** `ianatimezone.TzWasm32Wasi`
- **Similarity:** 0.25
- **Dependents:** 0
- **Priority Score:** 107.5
- **Functions:** 1/1 matched (target 4)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 2)
- **Missing types:** _none_

### 10. tz_illumos

- **Target:** `ianatimezone.TzIllumos`
- **Similarity:** 0.27
- **Dependents:** 0
- **Priority Score:** 107.3
- **Functions:** 1/1 matched (target 2)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

### 11. platform

- **Target:** `ianatimezone.Platform`
- **Similarity:** 0.29
- **Dependents:** 0
- **Priority Score:** 107.1
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

### 12. tz_wasm32_emscripten

- **Target:** `ianatimezone.TzWasm32Emscripten`
- **Similarity:** 0.42
- **Dependents:** 0
- **Priority Score:** 105.8
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

### 13. tz_haiku

- **Target:** `ianatimezone.TzHaiku`
- **Similarity:** 0.42
- **Dependents:** 0
- **Priority Score:** 105.8
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

### 14. tz_netbsd

- **Target:** `ianatimezone.TzNetbsd`
- **Similarity:** 0.43
- **Dependents:** 0
- **Priority Score:** 105.7
- **Functions:** 1/1 matched (target 2)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

### 15. tz_aix

- **Target:** `ianatimezone.TzAix`
- **Similarity:** 0.43
- **Dependents:** 0
- **Priority Score:** 105.7
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

### 16. tz_freebsd

- **Target:** `ianatimezone.TzFreebsd`
- **Similarity:** 0.62
- **Dependents:** 0
- **Priority Score:** 103.8
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present

## Reexport / Wiring Modules

These files match `reexport_modules` patterns in `.ast_distance_config.json`. They are filtered out of
normal priority and missing-file ladders because they are wiring
modules, not direct logic ports. Consult them for call-site routing;
do not treat them as the next implementation target by default.

### Missing

| Source | Expected target | Deps | Source path | Expected path |
|--------|-----------------|------|-------------|---------------|
| `lib` | `Lib` | 0 | `lib.rs` | `Lib.kt` |

