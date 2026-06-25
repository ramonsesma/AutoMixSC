AutoMixPlanner {
    *plan { |library, policy|
        var input;
        var settings;
        var requestedCount;
        var masterBpm;
        var transitionBars;
        var selected;
        var tracks;
        var transitions;

        input = library ? Array.new;
        settings = policy ? IdentityDictionary.new;
        requestedCount = settings[\count] ? input.size;
        masterBpm = settings[\masterBpm] ? 120;
        transitionBars = settings[\transitionBars] ? 4;

        selected = input.copyRange(0, (requestedCount - 1).max(0));
        tracks = selected.collect { |track, index|
            (
                id: track[\id],
                bpm: track[\bpm],
                key: track[\key],
                durationSec: track[\durationSec],
                deckIndex: index,
                targetBpm: masterBpm,
                gain: -6.0,
                pan: 0.0
            )
        };

        transitions = if(tracks.size > 1, {
            Array.fill(tracks.size - 1, {
                |i|
                (
                    index: i,
                    style: \fullCrossfade,
                    durationSec: transitionBars * 2,
                    sourceDeck: i,
                    targetDeck: i + 1
                )
            })
        }, {
            Array.new
        });

        ^(
            tracks: tracks,
            masterBpm: masterBpm,
            transitions: transitions,
            headroomDb: -6.0,
            totalDurationSec: tracks.collect(_.durationSec).sum,
            selection: (
                requestedCount: requestedCount,
                selectedCount: tracks.size,
                transitionBars: transitionBars
            )
        )
    }

    *report { |library, policy|
        ^(
            librarySize: (library ? Array.new).size,
            policy: policy,
            plan: this.plan(library, policy),
            warnings: Array.new,
            errors: Array.new
        )
    }
}
