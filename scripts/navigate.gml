///navigate(target)
var target = argument0;
if(target==noone){
    exit;
}
if(mp_grid_path(game.map,path,x,y,target.x,target.y,true)){
    var toX = (path_get_point_x(path,0)+path_get_point_x(path,1))/2;
    var toY = (path_get_point_y(path,0)+path_get_point_y(path,1))/2;
    var dir = point_direction(x,y,toX,toY)+random_range(-5,5);
    x+= lengthdir_x(spd*delta_time/1000000,dir);
    y+= lengthdir_y(spd*delta_time/1000000,dir);

    show_debug_message(string(path_get_point_x(path,0)) +" " + string(y));
}
