// AutoMixSC quickstart
// Plan first; playback/rendering can use the same deterministic plan.
(
~plan = AutoMixSC.plan([
    (id: \intro, bpm: 120, key: "A minor", durationSec: 120),
    (id: \lift, bpm: 124, key: "E minor", durationSec: 128)
], (
    count: 2,
    masterBpm: 124,
    transitionBars: 4
));

~plan.postln;
)

// If your deck/player backend is loaded, hand the plan to playback:
// AutoMixSC.play(~plan, nil);

// Or render the plan with your render backend:
// AutoMixSC.render(~plan, "mix.wav", \wav);
