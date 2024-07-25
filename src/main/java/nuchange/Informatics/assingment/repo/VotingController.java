package nuchange.Informatics.assingment.repo;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/voting")
public class VotingController {

    private final ConcurrentHashMap<String, Integer> votes = new ConcurrentHashMap<>();

    @RequestMapping(value = "/entercandidate/{name}",method = RequestMethod.POST)
    public String enterCandidate(@PathVariable String name) {
        votes.putIfAbsent(name, 0);
        return "Candidate " + name + " entered successfully";
    }

    @RequestMapping(value = "/castvote/{name}",method = RequestMethod.POST)
    public String castVote(@PathVariable String name) {
        if (!votes.containsKey(name)) {
            return "Invalid candidate name";
        }
        int count = votes.computeIfPresent(name, (k, v) -> v + 1);
        return "Vote cast for " + name + ". New count: " + count;
    }

    @RequestMapping(value = "/countvote/{name}",method = RequestMethod.GET)
    public String countVote(@PathVariable String name) {
        if (!votes.containsKey(name)) {
            return "Invalid candidate name";
        }
        return "Vote count for " + name + ": " + votes.get(name);
    }

    @GetMapping("/listvote")
    public Map<String, Integer> listVote() {
        return votes;
    }

    @GetMapping("/getwinner")
    public String getWinner() {
        if (votes.isEmpty()) {
            return "No candidates or votes yet";
        }

        int maxVotes = 0;
        List<String> winners = new ArrayList<>();

        for (int voteCount : votes.values()) {
            if (voteCount > maxVotes) {
                maxVotes = voteCount;
            }
        }

        for (Map.Entry<String, Integer> entry : votes.entrySet()) {
            if (entry.getValue() == maxVotes) {
                winners.add(entry.getKey());
            }
        }

        if (winners.size() > 1) {
            return "No winner - tie between " + String.join(", ", winners);
        } else if (winners.size() == 1) {
            return "winner is "+winners.get(0);
        } else {
            return "No winner";
        }
    }
}