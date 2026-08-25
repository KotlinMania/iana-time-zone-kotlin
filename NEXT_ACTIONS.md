# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 4/17 (23.5%)
- **Function parity:** 14/144 matched (target 23) — 9.7%
- **Class/type parity:** 3/13 matched (target 17) — 23.1%
- **Combined symbol parity:** 17/157 matched (target 40) — 10.8%
- **Average inline-code cosine:** 0.29 (function body across 4 matched files)
- **Average documentation cosine:** 0.52 (doc text across 4 matched files)
- **Cheat-zeroed Files:** 1
- **Critical Issues:** 3 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. tz_linux

- **Target:** `ianatimezone.OpenWrtConfig`
- **Similarity:** 0.17
- **Dependents:** 0
- **Priority Score:** 61108.3
- **Functions:** 3/8 matched (target 5)
- **Missing functions:** `get_timezone_inner`, `etc_timezone`, `etc_localtime`, `etc_config_system`, `from`
- **Types:** 2/3 matched
- **Missing types:** `Item`
- **Tests:** 1/1 matched

### 2. platform

- **Target:** `ianatimezone.Platform [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10110.0
- **Functions:** 0/1 matched (target 0)
- **Missing functions:** `get_timezone_inner`
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

### 3. lib

- **Target:** `ianatimezone.Lib`
- **Similarity:** 0.35
- **Dependents:** 0
- **Priority Score:** 606.5
- **Functions:** 5/5 matched (target 11)
- **Missing functions:** _none_
- **Types:** 1/1 matched (target 9)
- **Missing types:** _none_
- **Tests:** 1/1 matched

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

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present

