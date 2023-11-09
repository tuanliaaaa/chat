/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author lethi
 */
@Data
@AllArgsConstructor
public class UserRole {
    private Long userRoleID;
    private Long userID;
    private Long roleID;
}
