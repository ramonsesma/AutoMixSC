// Run from sclang with:
//   sclang quarks/AutoMixSC/tests/TestAutoMixSC.sc

TestAutoMixSC : UnitTest {

    test_quark_facade_exists {
        this.assert(AutoMixSC.notNil);
        this.assert(AutoMixSC.respondsTo(\plan));
        this.assert(AutoMixSC.respondsTo(\play));
        this.assert(AutoMixSC.respondsTo(\render));
        this.assert(AutoMixSC.respondsTo(\stop));
        this.assert(AutoMixSC.respondsTo(\inspect));
        this.assert(AutoMixSC.respondsTo(\report));
    }

    test_plan_is_deterministic_for_same_library_and_policy {
        var library;
        var policy;
        var first;
        var second;

        library = [
            (id: \a, bpm: 120, key: "A minor", durationSec: 120),
            (id: \b, bpm: 124, key: "E minor", durationSec: 130),
            (id: \c, bpm: 128, key: "C major", durationSec: 140)
        ];
        policy = (count: 2, masterBpm: 124, transitionBars: 4);

        first = AutoMixSC.plan(library, policy);
        second = AutoMixSC.plan(library, policy);

        this.assertEquals(first, second);
    }

    test_plan_reports_selection_metadata {
        var library;
        var plan;

        library = [
            (id: \a, bpm: 120, key: "A minor", durationSec: 120),
            (id: \b, bpm: 124, key: "E minor", durationSec: 130)
        ];
        plan = AutoMixSC.plan(library, (count: 2, masterBpm: 124));

        this.assert(plan.includesKey(\tracks));
        this.assert(plan.includesKey(\masterBpm));
        this.assert(plan.includesKey(\transitions));
        this.assert(plan.includesKey(\headroomDb));
    }

    test_play_returns_live_result {
        var plan;
        var result;

        plan = AutoMixSC.plan([
            (id: \a, bpm: 120, key: "A minor", durationSec: 120)
        ], (count: 1, masterBpm: 120));

        result = AutoMixSC.play(plan, nil);

        this.assert(result.includesKey(\ok));
        this.assert(result.includesKey(\mixId));
        this.assertEquals(result[\ok], true);
        this.assertEquals(result[\mode], \live);
    }

    test_render_returns_output_result {
        var plan;
        var result;

        plan = AutoMixSC.plan([
            (id: \a, bpm: 120, key: "A minor", durationSec: 120)
        ], (count: 1, masterBpm: 120));

        result = AutoMixSC.render(plan, "mix.wav", \wav);

        this.assert(result.includesKey(\ok));
        this.assert(result.includesKey(\path));
        this.assertEquals(result[\ok], true);
        this.assertEquals(result[\mode], \offline);
    }

    test_documentation_smoke_check {
        var root;

        root = [".", "..", "quarks/AutoMixSC"].detect({ |candidate|
            File.exists(candidate +/+ "README.md")
        });

        this.assert(root.notNil);
        this.assert(File.exists(root +/+ "README.md"));
        this.assert(File.exists(root +/+ "HelpSource/Classes/AutoMixPlanner.schelp"));
        this.assert(File.exists(root +/+ "HelpSource/Classes/AutoMixEngine.schelp"));
        this.assert(File.exists(root +/+ "HelpSource/Classes/AutoMixSC.schelp"));
        this.assert(File.exists(root +/+ "assets/supercollider-quarks-cover.png"));
        this.assert(File.exists(root +/+ "examples/quickstart.scd"));
    }

    test_plan_play_render_round_trip {
        var plan;
        var playResult;
        var renderResult;

        plan = AutoMixSC.plan([
            (id: \a, bpm: 120, key: "A minor", durationSec: 120),
            (id: \b, bpm: 124, key: "E minor", durationSec: 130)
        ], (count: 2, masterBpm: 124));

        playResult = AutoMixSC.play(plan, nil);
        renderResult = AutoMixSC.render(plan, "mix.wav", \wav);

        this.assert(playResult[\ok] == true);
        this.assert(renderResult[\ok] == true);
        this.assertEquals(renderResult[\path], "mix.wav");
    }

    test_report_returns_string {
        var report;

        report = AutoMixSC.report((ok: true, mode: \live));

        this.assert(report.isString);
    }
}
