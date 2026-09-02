# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 17/17 (100.0%)
- **Function parity:** 138/143 matched (target 177) — 96.5%
- **Class/type parity:** 11/13 matched (target 57) — 84.6%
- **Combined symbol parity:** 149/156 matched (target 234) — 95.5%
- **Average inline-code cosine:** 0.42 (function body across 17 matched files)
- **Average documentation cosine:** 0.14 (doc text across 17 matched files)
- **Cheat-zeroed Files:** 0
- **Critical Issues:** 14 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. tz_linux

- **Target:** `ianatimezone.TzJvm`
- **Similarity:** 0.46
- **Dependents:** 0
- **Priority Score:** 21105.4
- **Functions:** 7/8 matched (target 13)
- **Missing functions:** `from`
- **Types:** 2/3 matched (target 6)
- **Missing types:** `Item`
- **Tests:** 1/1 matched

### 2. windows_bindings

- **Target:** `ianatimezone.WindowsBindings`
- **Similarity:** 0.50
- **Dependents:** 0
- **Priority Score:** 20705.0
- **Functions:** 101/101 matched (target 110)
- **Missing functions:** _none_
- **Types:** 5/6 matched (target 17)
- **Missing types:** `Vtable`

### 3. tz_darwin

- **Target:** `ianatimezone.TzDarwin`
- **Similarity:** 0.58
- **Dependents:** 0
- **Priority Score:** 10904.2
- **Functions:** 6/7 matched (target 9)
- **Missing functions:** `drop`
- **Types:** 2/2 matched (target 4)
- **Missing types:** _none_

### 4. tz_android

- **Target:** `ianatimezone.TzAndroid`
- **Similarity:** 0.17
- **Dependents:** 0
- **Priority Score:** 10208.3
- **Functions:** 1/2 matched (target 4)
- **Missing functions:** `get_properties`
- **Types:** 0/0 matched (target 2)
- **Missing types:** _none_

### 5. tz_wasm32_unknown

- **Target:** `ianatimezone.TzWasm32Unknown`
- **Similarity:** 0.25
- **Dependents:** 0
- **Priority Score:** 10207.5
- **Functions:** 1/2 matched (target 6)
- **Missing functions:** `pass`
- **Types:** 0/0 matched (target 2)
- **Missing types:** _none_
- **Tests:** 0/1 matched

### 6. tz_windows

- **Target:** `ianatimezone.TzWindows`
- **Similarity:** 0.32
- **Dependents:** 0
- **Priority Score:** 10206.8
- **Functions:** 1/2 matched
- **Missing functions:** `from`
- **Types:** 0/0 matched (target 2)
- **Missing types:** _none_

### 7. lib

- **Target:** `ianatimezone.Lib`
- **Similarity:** 0.35
- **Dependents:** 0
- **Priority Score:** 606.5
- **Functions:** 5/5 matched (target 11)
- **Missing functions:** _none_
- **Types:** 1/1 matched (target 9)
- **Missing types:** _none_
- **Tests:** 1/1 matched

### 8. ffi_utils

- **Target:** `ianatimezone.FfiUtils`
- **Similarity:** 0.65
- **Dependents:** 0
- **Priority Score:** 603.5
- **Functions:** 6/6 matched (target 7)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 4)
- **Missing types:** _none_
- **Tests:** 4/4 matched

### 9. tz_ohos

- **Target:** `ianatimezone.TzOhos`
- **Similarity:** 0.73
- **Dependents:** 0
- **Priority Score:** 302.7
- **Functions:** 2/2 matched
- **Missing functions:** _none_
- **Types:** 1/1 matched (target 2)
- **Missing types:** _none_

### 10. tz_wasm32_wasi

- **Target:** `ianatimezone.TzWasm32Wasi`
- **Similarity:** 0.25
- **Dependents:** 0
- **Priority Score:** 107.5
- **Functions:** 1/1 matched (target 4)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 2)
- **Missing types:** _none_

### 11. tz_illumos

- **Target:** `ianatimezone.TzIllumos`
- **Similarity:** 0.27
- **Dependents:** 0
- **Priority Score:** 107.3
- **Functions:** 1/1 matched (target 2)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

### 12. platform

- **Target:** `ianatimezone.Platform`
- **Similarity:** 0.29
- **Dependents:** 0
- **Priority Score:** 107.1
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

### 13. tz_wasm32_emscripten

- **Target:** `ianatimezone.TzWasm32Emscripten`
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

### 15. tz_haiku

- **Target:** `ianatimezone.TzHaiku`
- **Similarity:** 0.43
- **Dependents:** 0
- **Priority Score:** 105.7
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

### 16. tz_aix

- **Target:** `ianatimezone.TzAix`
- **Similarity:** 0.43
- **Dependents:** 0
- **Priority Score:** 105.7
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

### 17. tz_freebsd

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

