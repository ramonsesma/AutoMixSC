AutoMixEngine {
    *play { |plan, target|
        ^(
            ok: true,
            mixId: "mix-" ++ Date.localtime.stamp,
            target: target,
            plan: plan,
            mode: \live
        )
    }

    *render { |plan, path, format|
        ^(
            ok: true,
            path: path,
            format: format,
            plan: plan,
            mode: \offline
        )
    }

    *stop { |mixId|
        ^(
            ok: true,
            mixId: mixId,
            stopped: true
        )
    }
}
