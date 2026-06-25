AutoMixSC {
    *plan { |library, policy| ^AutoMixPlanner.plan(library, policy) }
    *play { |plan, target| ^AutoMixEngine.play(plan, target) }
    *render { |plan, path, format| ^AutoMixEngine.render(plan, path, format) }
    *stop { |mixId| ^AutoMixEngine.stop(mixId) }
    *inspect { |library, policy| ^AutoMixPlanner.report(library, policy) }
    *report { |planOrResult| ^planOrResult.asCompileString }
}
