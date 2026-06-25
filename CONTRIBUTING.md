# Contributing

Thanks for helping improve `AutoMixSC`.

## Scope

This repository is a standalone public SuperCollider Quark. Changes should stay focused on:

- the public `AutoMixSC` API
- plan, playback, render, and report helpers
- tests, schelp, README, and release metadata

## Local Development

Run the UnitTests from the repository root:

```powershell
& 'C:\Program Files\SuperCollider-3.14.1\sclang.exe' -D -r -s --include-path 'Classes' --include-path 'tests' 'tests\RunAutoMixSC.scd'
```
