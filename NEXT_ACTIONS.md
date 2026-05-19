# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 17/17 (100.0%)
- **Function parity:** 129/143 matched (target 154) — 90.2%
- **Class/type parity:** 11/13 matched (target 48) — 84.6%
- **Combined symbol parity:** 140/156 matched (target 202) — 89.7%
- **Average inline-code cosine:** 0.51 (function body across 17 matched files)
- **Average documentation cosine:** 0.21 (doc text across 17 matched files)
- **Cheat-zeroed Files:** 2
- **Critical Issues:** 7 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. ffi_utils

- **Target:** `ianatimezone.FfiUtils [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 40610.0
- **Functions:** 2/6 matched (target 7)
- **Missing functions:** `test_android_timezone_property_name_is_valid_cstr`, `test_android_timezone_property_name_getter`, `test_tzname_buffer_fits_all_iana_names`, `test_tzname_buffer_correct_size`
- **Types:** 0/0 matched (target 3)
- **Missing types:** _none_
- **Tests:** 0/4 matched

### 2. lib

- **Target:** `ianatimezone.Lib`
- **Similarity:** 0.20
- **Dependents:** 0
- **Priority Score:** 40608.0
- **Functions:** 1/5 matched (target 3)
- **Missing functions:** `source`, `fmt`, `from`, `get_current`
- **Types:** 1/1 matched (target 4)
- **Missing types:** _none_
- **Tests:** 0/1 matched

### 3. tz_linux

- **Target:** `ianatimezone.TzLinux`
- **Similarity:** 0.44
- **Dependents:** 0
- **Priority Score:** 31105.6
- **Functions:** 6/8 matched (target 7)
- **Missing functions:** `from`, `test_read_word`
- **Types:** 2/3 matched (target 4)
- **Missing types:** `Item`
- **Tests:** 0/1 matched

### 4. windows_bindings

- **Target:** `ianatimezone.WindowsBindings`
- **Similarity:** 0.50
- **Dependents:** 0
- **Priority Score:** 20705.0
- **Functions:** 101/101 matched (target 110)
- **Missing functions:** _none_
- **Types:** 5/6 matched (target 17)
- **Missing types:** `Vtable`

### 5. tz_darwin

- **Target:** `ianatimezone.TzDarwin`
- **Similarity:** 0.64
- **Dependents:** 0
- **Priority Score:** 10903.6
- **Functions:** 6/7 matched (target 8)
- **Missing functions:** `drop`
- **Types:** 2/2 matched (target 6)
- **Missing types:** _none_

### 6. tz_windows

- **Target:** `ianatimezone.TzWindows`
- **Similarity:** 0.36
- **Dependents:** 0
- **Priority Score:** 10206.4
- **Functions:** 1/2 matched (target 1)
- **Missing functions:** `from`
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

### 7. tz_wasm32_unknown

- **Target:** `ianatimezone.TzWasm32Unknown`
- **Similarity:** 0.75
- **Dependents:** 0
- **Priority Score:** 10202.5
- **Functions:** 1/2 matched (target 4)
- **Missing functions:** `pass`
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_
- **Tests:** 0/1 matched

### 8. platform

- **Target:** `ianatimezone.Platform [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10110.0
- **Functions:** 0/1 matched (target 0)
- **Missing functions:** `get_timezone_inner`
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

### 9. tz_ohos

- **Target:** `ianatimezone.TzOhos`
- **Similarity:** 0.80
- **Dependents:** 0
- **Priority Score:** 302.0
- **Functions:** 2/2 matched
- **Missing functions:** _none_
- **Types:** 1/1 matched (target 2)
- **Missing types:** _none_

### 10. tz_android

- **Target:** `ianatimezone.TzAndroid`
- **Similarity:** 0.78
- **Dependents:** 0
- **Priority Score:** 202.2
- **Functions:** 2/2 matched (target 5)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 2)
- **Missing types:** _none_

### 11. tz_wasm32_wasi

- **Target:** `ianatimezone.TzWasm32Wasi`
- **Similarity:** 0.25
- **Dependents:** 0
- **Priority Score:** 107.5
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

### 12. tz_netbsd

- **Target:** `ianatimezone.TzNetbsd`
- **Similarity:** 0.61
- **Dependents:** 0
- **Priority Score:** 103.9
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

### 13. tz_wasm32_emscripten

- **Target:** `ianatimezone.TzWasm32Emscripten`
- **Similarity:** 0.61
- **Dependents:** 0
- **Priority Score:** 103.9
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

### 14. tz_haiku

- **Target:** `ianatimezone.TzHaiku`
- **Similarity:** 0.62
- **Dependents:** 0
- **Priority Score:** 103.8
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

### 15. tz_aix

- **Target:** `ianatimezone.TzAix`
- **Similarity:** 0.62
- **Dependents:** 0
- **Priority Score:** 103.8
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

### 16. tz_illumos

- **Target:** `ianatimezone.TzIllumos`
- **Similarity:** 0.71
- **Dependents:** 0
- **Priority Score:** 102.9
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

### 17. tz_freebsd

- **Target:** `ianatimezone.TzFreebsd`
- **Similarity:** 0.74
- **Dependents:** 0
- **Priority Score:** 102.6
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

## Next Commands

```bash
# Initialize task queue for systematic porting
cd tools/ast_distance
./ast_distance --init-tasks ../../tmp/iana-time-zone/src rust ../../src kotlin tasks.json ../../AGENTS.md

# Get next high-priority task
./ast_distance --assign tasks.json <agent-id>
```
