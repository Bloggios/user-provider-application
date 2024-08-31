package com.bloggios.user.controller;

import com.bloggios.authenticationconfig.payload.AuthenticatedUser;
import com.bloggios.user.constants.EndpointConstants;
import com.bloggios.user.payload.response.ExceptionResponse;
import com.bloggios.user.payload.response.FollowCountResponse;
import com.bloggios.user.payload.response.FollowResponse;
import com.bloggios.user.service.FollowService;
import com.bloggios.user.utils.AsyncUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - user-provider-application
 * Package - com.bloggios.user.controller
 * Created_on - June 02 - 2024
 * Created_at - 20:50
 */

@RestController
@RequestMapping(EndpointConstants.FollowController.BASE_PATH)
@RequiredArgsConstructor
@Tag(
        name = "Follow Controller",
        description = "Controller responsible for handler user followers, followings, etc"
)
public class FollowController {

    private final FollowService followService;

    @GetMapping(EndpointConstants.FollowController.HANDLE_FOLLOW)
    @Operation(
            responses = {
                    @ApiResponse(description = "SUCCESS", responseCode = "200", content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = FollowResponse.class)
                    )),
                    @ApiResponse(description = "No Content", responseCode = "401", content = {
                            @Content(schema = @Schema(implementation = Void.class))
                    }),
                    @ApiResponse(description = "FORBIDDEN", responseCode = "403", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                    }),
                    @ApiResponse(description = "BAD REQUEST", responseCode = "400", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    })
            },
            security = {
                    @SecurityRequirement(
                            name = "bearerAuth"
                    )
            }
    )
    public ResponseEntity<FollowResponse> handleFollow(@PathVariable String userId, @AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        return ResponseEntity.ok(AsyncUtils.getAsyncResult(followService.handleFollow(userId, authenticatedUser)));
    }

    @GetMapping(EndpointConstants.FollowController.COUNT_FOLLOW)
    @Operation(
            responses = {
                    @ApiResponse(description = "SUCCESS", responseCode = "200", content = @Content(
                            mediaType = "application/json", schema = @Schema(implementation = FollowCountResponse.class)
                    )),
                    @ApiResponse(description = "No Content", responseCode = "401", content = {
                            @Content(schema = @Schema(implementation = Void.class))
                    }),
                    @ApiResponse(description = "FORBIDDEN", responseCode = "403", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                    }),
                    @ApiResponse(description = "BAD REQUEST", responseCode = "400", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
                    })
            }
    )
    public ResponseEntity<FollowCountResponse> followCount(@AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        return ResponseEntity.ok(AsyncUtils.getAsyncResult(followService.countFollowerFollowing(authenticatedUser)));
    }
}
