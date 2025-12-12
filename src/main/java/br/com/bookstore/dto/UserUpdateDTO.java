package br.com.bookstore.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserUpdateDTO(
    @Schema(example = "RNSgnaolin")
    String name, 
    @Schema(example = "RNS")
    String login, 
    @Schema(example = "!!ch4nG3d!!")
    String password) {
    
}
