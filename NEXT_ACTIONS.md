# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 10/17 (58.8%)
- **Function parity:** 129/143 matched (target 166) — 90.2%
- **Class/type parity:** 10/13 matched (target 49) — 76.9%
- **Combined symbol parity:** 139/156 matched (target 215) — 89.1%
- **Average inline-code cosine:** 0.30 (function body across 10 matched files)
- **Average documentation cosine:** 0.20 (doc text across 10 matched files)
- **Cheat-zeroed Files:** 3
- **Critical Issues:** 9 files with <0.60 function similarity

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

### 4. tz_wasm32_unknown

- **Target:** `ianatimezone.TzWasm32Unknown [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10210.0
- **Functions:** 1/2 matched (target 6)
- **Missing functions:** `pass`
- **Types:** 0/0 matched (target 2)
- **Missing types:** _none_
- **Tests:** 0/1 matched

### 5. tz_android

- **Target:** `ianatimezone.TzAndroid`
- **Similarity:** 0.17
- **Dependents:** 0
- **Priority Score:** 10208.3
- **Functions:** 1/2 matched (target 4)
- **Missing functions:** `get_properties`
- **Types:** 0/0 matched (target 2)
- **Missing types:** _none_

### 6. tz_windows

- **Target:** `ianatimezone.TzWindows`
- **Similarity:** 0.32
- **Dependents:** 0
- **Priority Score:** 10206.8
- **Functions:** 1/2 matched
- **Missing functions:** `from`
- **Types:** 0/0 matched (target 2)
- **Missing types:** _none_

### 7. platform

- **Target:** `ianatimezone.Platform [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10110.0
- **Functions:** 0/1 matched (target 0)
- **Missing functions:** `get_timezone_inner`
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

### 8. lib

- **Target:** `ianatimezone.Lib`
- **Similarity:** 0.35
- **Dependents:** 0
- **Priority Score:** 606.5
- **Functions:** 5/5 matched (target 11)
- **Missing functions:** _none_
- **Types:** 1/1 matched (target 9)
- **Missing types:** _none_
- **Tests:** 1/1 matched

### 9. ffi_utils

- **Target:** `ianatimezone.FfiUtils`
- **Similarity:** 0.65
- **Dependents:** 0
- **Priority Score:** 603.5
- **Functions:** 6/6 matched (target 7)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 4)
- **Missing types:** _none_
- **Tests:** 4/4 matched

### 10. tz_wasm32_wasi

- **Target:** `ianatimezone.TzWasm32Wasi [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 110.0
- **Functions:** 1/1 matched (target 4)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 2)
- **Missing types:** _none_

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present

