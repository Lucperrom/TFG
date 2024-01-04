package org.springframework.samples.pubus.paper;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;

import org.apache.catalina.valves.rewrite.InternalRewriteMap.LowerCase;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.pubus.auth.payload.response.MessageResponse;
import org.springframework.samples.pubus.exceptions.AccessDeniedException;
import org.springframework.samples.pubus.exceptions.ResourceNotOwnedException;
import org.springframework.samples.pubus.paper.exceptions.DuplicatedPaperTitleException;
import org.springframework.samples.pubus.user.User;
import org.springframework.samples.pubus.user.UserService;
import org.springframework.samples.pubus.util.RestPreconditions;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/papers")
@Tag(name = "Papers", description = "The Paper management API")
@SecurityRequirement(name = "bearerAuth")
public class PaperRestController {

	private final PaperService paperService;
	private final UserService userService;
	private static final String USER_AUTH = "user";
	private static final String ADMIN_AUTH = "admin";

	@Autowired
	public PaperRestController(PaperService paperService, UserService userService) {
		this.paperService = paperService;
		this.userService = userService;
	}

	@InitBinder("paper")
	public void initPaperBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new PaperValidator());
	}

	@GetMapping("/mine/{userId}")
	public ResponseEntity<List<Paper>> findAllMine(@RequestParam(required = false) Integer userId) {
		User user = userService.findCurrentUser();
		if (userId != null) {
			if (user.getId().equals(userId) || user.hasAuthority(ADMIN_AUTH).equals(true))
				return new ResponseEntity<>(paperService.findAllPapersByUserId(userId), HttpStatus.OK);
		} else {
			if (user.hasAuthority(ADMIN_AUTH).equals(true))
				return new ResponseEntity<>((List<Paper>) this.paperService.findAll(), HttpStatus.OK);
		}
		throw new AccessDeniedException();
	}

	@GetMapping
	public ResponseEntity<List<Paper>> findAll() {
		return new ResponseEntity<>((List<Paper>) this.paperService.findAll(), HttpStatus.OK);
	}

	@GetMapping("/types/{paperType}")
	public ResponseEntity<List<Paper>> findAllPapersByType(@RequestParam String paperType) {
		return new ResponseEntity<>((List<Paper>) this.paperService.findAllPapersByType(paperType), HttpStatus.OK);
	}

	@GetMapping("/filtered/{originalSearch}")
	public ResponseEntity<List<Paper>> searchPaper(@RequestParam String search) {
		List<Paper> list1 = this.paperService.findAllPapersByUserFirstName(search);
		List<Paper> list2 = this.paperService.findAllPapersByUserLastName(search);
		List<Paper> list3 = this.paperService.findAllPapersAbstractWord(search);
		List<Paper> list4 = this.paperService.findAllPapersByKeyword(search);
		List<Paper> list_complete = new ArrayList<>();
		list_complete.addAll(list1);
		list_complete.addAll(list2);
		list_complete.addAll(list3);
		list_complete.addAll(list4);

		return new ResponseEntity<>((List<Paper>) list_complete, HttpStatus.OK);
	}

	@GetMapping("types")
	public ResponseEntity<List<PaperType>> findAllTypes() {
		List<PaperType> res = (List<PaperType>) paperService.findPaperTypes();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Paper> create(@RequestBody @Valid Paper paper)
			throws DataAccessException, DuplicatedPaperTitleException {
		User user = userService.findCurrentUser();
		Paper newPaper = new Paper();
		Paper savedPaper;
		BeanUtils.copyProperties(paper, newPaper, "id");
		if (user.hasAuthority(USER_AUTH).equals(true)) {
			newPaper.setUser(user);
		}

		savedPaper = this.paperService.savePaper(newPaper);

		return new ResponseEntity<>(savedPaper, HttpStatus.CREATED);
	}

	@PutMapping("{paperId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Paper> update(@PathVariable("paperId") int paperId, @RequestBody @Valid Paper paper) {
		Paper aux = RestPreconditions.checkNotNull(paperService.findPaperById(paperId), "Paper", "ID", paperId);
		User loggedUser = userService.findCurrentUser();
		if (loggedUser.hasAuthority(USER_AUTH).equals(true)) {
			User paperUser = aux.getUser();
			if (loggedUser.getId().equals(paperUser.getId())) {
				Paper res = this.paperService.updatePaper(paper, paperId);
				return new ResponseEntity<>(res, HttpStatus.OK);
			} else
				throw new ResourceNotOwnedException(aux);
		} else {
			Paper res = this.paperService.updatePaper(paper, paperId);
			return new ResponseEntity<>(res, HttpStatus.OK);
		}

	}

// //GET BY ID

// 	@GetMapping("{paperId}")
// 	public ResponseEntity<Paper> findById(@PathVariable("paperId") int paperId) {
// 		Paper paper = RestPreconditions.checkNotNull(paperService.findPaperById(paperId), "Paper", "ID", paperId);
// 		User user = userService.findCurrentUser();
// 		if (user.hasAuthority(RESEARCHER_AUTH).equals(true)) {
// 			Researcher loggedResearcher = userService.findResearcherByUser(user.getId());
// 			Researcher paperResearcher = paper.getResearcher();
// 			if (loggedResearcher.getId().equals(paperResearcher.getId()))
// 				return new ResponseEntity<>(this.paperService.findPaperById(paperId), HttpStatus.OK);
// 			else
// 				throw new ResourceNotOwnedException(paper);
// 		} else {
// 			return new ResponseEntity<>(this.paperService.findPaperById(paperId), HttpStatus.OK);
// 		}
// 	}

//DELETE	

	@DeleteMapping("{paperId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<MessageResponse> delete(@PathVariable("paperId") int paperId) {
		Paper paper = RestPreconditions.checkNotNull(paperService.findPaperById(paperId), "Paper", "ID", paperId);
		User loggedUser = userService.findCurrentUser();
		if (loggedUser.hasAuthority(USER_AUTH).equals(true)) {
			User paperUser = paper.getUser();
			if (loggedUser.getId().equals(paperUser.getId())) {
				paperService.deletePaper(paperId);
				return new ResponseEntity<>(new MessageResponse("Paper deleted!"), HttpStatus.OK);
			} else
				throw new ResourceNotOwnedException(paper);
		} else {
			paperService.deletePaper(paperId);
			return new ResponseEntity<>(new MessageResponse("Paper deleted!"), HttpStatus.OK);
		}
	}


}