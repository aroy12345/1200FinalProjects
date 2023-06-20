(** The main paint application *)

;; open Gctx
;; open Widget

(******************************************)
(**    SHAPES, MODES, and PROGRAM STATE   *)
(******************************************)

(** A location in the paint_canvas widget *)
type point = position  (* from Gctx *)

(** The shapes that are visible in the paint canvas -- these make up the
    picture that the user has drawn, as well as any other "visible" elements
    that must show up in the canvas area (e.g. a "selection rectangle"). At
    the start of the homework, the only available shape is a line.  *)
(* TODO: You will modify this definition in Tasks 3, 4, 5 and maybe 6. *)
type shape = 
  | Line of {color: color; width: int; p1: point; p2: point}
  | Points of { color: Gctx.color; width: int; points: point list }
  | Elipse of { color: Gctx.color; width: int; p1:point; radX: int; radY: 
  int; starting:point}
  | Rect of { color: Gctx.color; width: int; starting:point; hori: int; verti: 
  int; }
  
(** These are the possible interaction modes that the paint program might be
    in. Some interactions require two modes. For example, the GUI might
    recognize the first mouse click as starting a line and a second mouse
    click as finishing the line.

    To start out, there are only two modes:

      - LineStartMode means the paint program is waiting for the user to make
        the first click to start a line.

      - LineEndMode means that the paint program is waiting for the user's
        second click. The point associated with this mode stores the location
        of the user's first mouse click.  *)
(* TODO: You will need to modify this type in Tasks 3 and 4, and maybe 6. *)
type mode = 
  | LineStartMode
  | LineEndMode of point
  | PointMode
  | ElipseMode
  | RectMode of point



(** The state of the paint program. *)
type state = {
  (** The sequence of all shapes drawn by the user, in order from
      least recent (the head) to most recent (the tail). *)
  shapes : shape Deque.deque;

  (** The input mode the Paint program is in. *)
  mutable mode : mode;

 mutable mode2 : mode;

 mutable mode3: mode;

mutable mode4: mode;

  (** The currently selected pen color. *)
  mutable color : color;

  mutable width : int;

  mutable preview : shape option;

  mutable preview2: shape option;

  mutable preview3: shape option;

   mutable preview4: shape option;
   

  (* TODO: You will need to add new state for Tasks 2, 5, and *)
  (* possibly 6 *) 
}

(** Initial values of the program state. *)




let paint : state = {
  shapes = Deque.create ();
  mode = LineStartMode;
  mode2 = LineStartMode;
  mode3 = LineStartMode;
  mode4 = LineStartMode;
  color = black;
  preview = None;
  preview2= Some(Rect {color = black; width = 0;  starting = (0,0); hori = 0; 
  verti = 0});
  preview3 = Some(Rect {color = black; width = 0;  starting = (0,0); hori = 0; 
  verti = 0});
  preview4 =  Some(Rect {color = black; width = 0;  starting = (0,0); hori = 0; 
  verti = 0});
  width =0;
  
  
  (* TODO: You will need to add new state for Tasks 2, 5, and maybe 6 *)
  
}





(** This function creates a graphics context with the appropriate
    pen color. *)
(* TODO: Your will need to modify this function in Task 5 *)
let with_params (g: gctx) (c: color)(w:int) : gctx =
  let g = with_width(g)(w) in
  let g = with_color(g)(c) in
  g


(*********************************)
(**    MAIN CANVAS REPAINTING    *)
(*********************************)

(** The paint_canvas repaint function.

    This function iterates through all the drawn shapes (in order of least
    recent to most recent so that they are layered on top of one another
    correctly) and uses the Gctx.draw_xyz functions to display them on the
    canvas.  *)

(* TODO: You will need to modify this repaint function in Tasks 2, 3,
   4, and possibly 5 or 6. For example, if the user is performing some
   operation that provides "preview" (see Task 2) the repaint function
   must also show the preview. *)


let repaint (g: gctx) : unit =
  let draw_shape (s: shape) : unit =
   
  

    begin match (s, paint.preview) with
      
      
      | (Line l, Some Line s) -> draw_line (with_params g l.color l.width) 
      l.p1 l.p2; draw_line (with_params g s.color s.width) s.p1 s.p2;
      | (Line l, None) -> draw_line (with_params g l.color l.width) l.p1 l.p2; 
      | (Line l, Some Points s) -> draw_line (with_params g l.color l.width)
       l.p1 l.p2; draw_points (with_params g s.color s.width) (s.points);
      | (Line l, Some Elipse s) -> draw_line (with_params g l.color l.width) 
      l.p1 l.p2; draw_ellipse 
      (with_params g s.color s.width) (s.p1)(s.radX)(s.radY); 
      | (Points l, None) -> 
      draw_points (with_params g l.color l.width) (l.points); 
      | (Points l, Some Points s) ->  
      draw_points (with_params g l.color l.width) l.points; 
      draw_points (with_params g s.color s.width) (s.points); 
      | (Points l, Some Line s) -> 
      draw_points (with_params g l.color l.width) (l.points);  
      draw_line (with_params g s.color s.width) s.p1 s.p2; 
      | (Points l, Some Elipse s) -> 
      draw_points (with_params g l.color l.width) (l.points); 
      draw_ellipse (with_params g s.color s.width) (s.p1)(s.radX)(s.radY); 
      | (Elipse l, None) -> 
      draw_ellipse (with_params g l.color l.width) (l.p1)(l.radX)(l.radY); 
      | (Elipse l, Some Points s) -> 
      draw_ellipse (with_params g l.color l.width) (l.p1)(l.radX)(l.radY); 
      draw_points (with_params g s.color s.width) (s.points); 
      | (Elipse l, Some Line s) -> 
      draw_ellipse (with_params g l.color l.width) (l.p1)(l.radX)(l.radY);
       draw_line (with_params g s.color s.width) s.p1 s.p2; 
      | (Elipse l, Some Elipse s) -> 
      draw_ellipse (with_params g l.color l.width) (l.p1)(l.radX)(l.radY); 
      draw_ellipse (with_params g s.color s.width) (s.p1)(s.radX)(s.radY); 
      | (_,_) -> ()
    end 
    in 
  Deque.iterate draw_shape paint.shapes

(*repaint function for the three sliders *)
let repaint2(g:gctx): unit =
       begin match paint.preview2 with 
       | Some Rect r -> 
       fill_rect(with_params g r.color r.width) (r.starting) (r.hori, r.verti);
       | _  -> ();
       end

    let repaint3(g:gctx): unit =
       begin match paint.preview3 with 
       | Some Rect r -> 
       fill_rect(with_params g r.color r.width) (r.starting) (r.hori, r.verti);
       | _  -> ();
       end
  
    let repaint4(g:gctx): unit =
       begin match paint.preview4 with 
       | Some Rect r -> 
       fill_rect(with_params g r.color r.width) (r.starting) (r.hori, r.verti);
       | _  -> ();
       end

       
        

(** Create the actual paint_canvas widget and its associated
    notifier_controller . *)
let ((paint_canvas : widget), 
(paint_canvas_controller : notifier_controller)) =
  canvas (600, 270) repaint


let ((slider_canvas : widget), 
(slider_canvas_controller : notifier_controller)) =
  canvas (300, 25) repaint2


  let ((slider_canvas2 : widget), 
  (slider_canvas_controller2 : notifier_controller)) =
  canvas (300, 25) repaint3

    let ((slider_canvas3 : widget), 
    (slider_canvas_controller3 : notifier_controller)) =
  canvas (300, 25) repaint4
 
(* each of the three slider canvas*)
 
(************************************)
(**  PAINT CANVAS EVENT HANDLER     *)
(************************************)

(** The paint_action function processes all events that occur
    in the canvas region. *)
(* TODO: Tasks 2, 3, 4, 5, and 6 involve changes to paint_action. *)


let (w, user) = checkbox true " X "



  

(*each of the three action for the three slider *)
let slider_action(gc: gctx)(event: event): unit =
  let (x1,_) = event_pos event gc in
   begin match(event_type event) with
   |MouseDown-> paint.mode2<- RectMode (0,0);
   |MouseDrag -> (begin match paint.mode2 with
                 | RectMode (0,0) -> if(x1<=300 && x1>=0) 
                 then (paint.preview2<-
                 Some(Rect {color = red; width = 0;  
                 starting = (0,0); hori = x1; verti = 25});
                   paint.color<- {r = ((255*x1*25)/(300*25)); 
                   g = paint.color.g; b =paint.color.b};) 
                   else if(x1>=0) then(
                   paint.preview2<-
                   Some(Rect {color = red; width = 0;  
                   starting = (0,0); hori = 300; verti = 25});
                   paint.color<- 
                   {r = ((255*300*25)/(300*25)); 
                   g = paint.color.g; b =paint.color.b};
                   )
                   else(
                   paint.preview2<-
                   Some(Rect {color = red; width = 0;  
                   starting = (0,0); hori = 0; verti = 25});
                   paint.color<- 
                   {r = ((255*300*25)/(300*25)); 
                   g = paint.color.g; b =paint.color.b};
                   );


                 | _ -> ();
                 end);
   
   |MouseUp -> (begin match paint.preview2 with 
                | Some Rect using -> 
                paint.color<- {r = ((255*using.hori*using.verti)/(300*25)); 
                g = paint.color.g; b =paint.color.b};
                |_ -> ()
                 end );
             paint.mode2<-LineStartMode;
   | _ ->();
   end


   let slider_action2(gc: gctx)(event: event): unit =
  let (x1,_) = event_pos event gc in
   begin match(event_type event) with
   |MouseDown-> paint.mode3<- RectMode (0,0);
   |MouseDrag -> (begin match paint.mode3 with
                 | RectMode (0,0) -> if(x1<=300&&x1>=0) 
                 then (paint.preview3<-
                 Some(Rect {color = green; width = 0;  
                 starting = (0,0); hori = x1; verti = 25});
                 paint.color<- 
                 {r = paint.color.r; g = ((255*x1*25)/(300*25)); 
                 b =paint.color.b};)
                 else if(x1>=0) then (
                 paint.preview3<-
                 Some(Rect {color = green; width = 0;  
                 starting = (0,0); hori = 300; verti = 25});
                 paint.color<- {r = paint.color.r; 
                 g = ((255*300*25)/(300*25)); b =paint.color.b};
                 )
                  else (
                 paint.preview3<-
                 Some(Rect {color = green; width = 0;  
                 starting = (0,0); hori = 0; verti = 25});
                 paint.color<- {r = paint.color.r; 
                 g = ((255*300*25)/(300*25)); b =paint.color.b};
                 );


                 | _ -> ();
                 end);
   
   |MouseUp -> (begin match paint.preview3 with 
                | Some Rect using ->
                 paint.color
                 <- {r = paint.color.r; 
                 g = ((255*using.hori*using.verti)/(300*25)); b =paint.color.b};
                |_ -> ()
                 end );
             paint.mode3<-LineStartMode;
   | _ ->();
   end

     let slider_action3(gc: gctx)(event: event): unit =
  let (x1,_) = event_pos event gc in
   begin match(event_type event) with
   |MouseDown-> paint.mode4<- RectMode (0,0);
   |MouseDrag -> (begin match paint.mode4 with
                 | RectMode (0,0) -> 
                 if(x1<=300&&x1>=0) 
                 then (paint.preview4<-
                 Some(Rect {color = blue; width = 0; 
                  starting = (0,0); hori = x1; verti = 25});
                 paint.color<- 
                 {r = paint.color.r;
                 g = paint.color.g; b =((255*x1*25)/(300*25))};)
                  else if(x1>=0) then (
                  paint.preview4<-
                  Some(Rect {color = blue; width = 0; 
                   starting = (0,0); hori = 300; verti = 25});
                 paint.color<- 
                 {r = paint.color.r; g = paint.color.g; 
                 b =((255*300*25)/(300*25))};)
                 else 
                  (
                  paint.preview4<-
                  Some(Rect {color = blue; width = 0; 
                   starting = (0,0); hori = 0; verti = 25});
                 paint.color<- 
                 {r = paint.color.r; g = paint.color.g; 
                 b =((255*300*25)/(300*25))};);

                 | _ -> ();
                 end);
   
   |MouseUp -> (begin match paint.preview4 with 
                | Some Rect using ->
                 paint.color<- 
                 {r = paint.color.r; g = paint.color.g; 
                 b =((255*using.hori*using.verti)/(300*25))};
                |_ -> ()
                 end );
             paint.mode4<-LineStartMode;
   | _ ->();
   end





  let paint_action (gc: gctx)(event: event) : unit =
   let p  = event_pos event gc in 
   


   if(Deque.is_empty(paint.shapes)) then (Deque.insert_tail 
            (Line {color=white; width = paint.width; p1=(0,0); p2=(0,0)}) 
            paint.shapes); (* shape isn't previewed when theres no shape in
            queue so to prevent this this line of code was added *)

             

  begin match (event_type event) with
    | MouseDown ->  
        
        if(user.get_value()) then (paint.width <- 15) else (paint.width <- 0);
      
       (begin match paint.mode with 
          | LineStartMode ->
          
            (paint.mode <- LineEndMode p);
          | PointMode -> paint.preview 
          <- Some (Points {color=paint.color; width = paint.width; 
          points = [p]});

          |ElipseMode -> paint.preview
           <-Some(Elipse{color = paint.color; 
           width = paint.width; p1 =p; radX =0; radY =0; starting = p})

          |_ ->
           
            ()
      

       end);
     





    | MouseDrag -> 
      
      (begin match paint.mode with 
          | LineStartMode ->
           
            ();
          

          | PointMode -> 
        
        let points_list = (begin match paint.preview with
        | Some (Points ps) -> ps.points
         | _ -> []
         end) in paint.preview <- 
         Some (Points{color = paint.color; 
         width = paint.width; points = points_list @ [p]});



          | LineEndMode p1 ->
            
          
            paint.preview <- 
            Some (Line {color=paint.color; width = paint.width; p1=p1; p2=p});

          |RectMode p->()
          |ElipseMode ->
             ( let start = (begin match paint.preview with
                   | Some(Elipse ps) -> ps.starting;
                   | _ -> p
                   end) in

                   let midX = (fst(p)+fst(start))/2
                   in let midY = (snd(p)+snd(start))/2
                   in let center = (midX, midY)
                   in let radiusX = abs((fst(p)-fst(start))/2) in
  
                   let radiusY = abs((snd(p)-snd(start))/2)
                   in paint.preview 
                   <- 
                   Some(Elipse{color = paint.color; width = paint.width; 
                   p1 = center; radX = radiusX; radY = radiusY; 
                   starting = start})

          
                   
                   
                   
                   ); 

           
      

       end);
     
      
     
     
     
     
    

    | MouseUp ->  
      
     (begin match paint.mode with 
          | LineStartMode ->
           
            paint.mode <- LineEndMode p
         
         | PointMode ->  
         
          let points_list = (begin match paint.preview with
        | Some (Points ps) -> ps.points
         | _ -> []
         end) 
         in Deque.insert_tail 
         (Points{color = paint.color; 
         width = paint.width; points = points_list}) paint.shapes;

         paint.preview <- None;
 
 
      |RectMode p->()
           
        |ElipseMode -> 
           (begin match paint.preview with 
              | Some Elipse s -> Deque.insert_tail 
              (Elipse{color = s.color; 
              width = paint.width; p1 = s.p1; radX = s.radX; radY = s.radY; 
              starting = s.starting}) paint.shapes; paint.preview <-None
              | _ -> ();
              end)



         
          | LineEndMode p1 ->
            (* The paint_canvas was waiting for the second click of a line,
              so create the line and add it to the deque of shapes. Go back
              to waiting for the first click. *)
            
          
           
            paint.preview <- None;
            Deque.insert_tail 
            (Line {color=paint.color; width = paint.width; p1=p1; p2=p}) 
            paint.shapes;
            paint.mode <- LineStartMode
         
       end);
      




      
          
        
      (* In this case there was a mouse button release event. TODO: Tasks 2, 
         3, 4, and possibly 6 need to do something different here. *) ()
    
    | _ -> ()
  
  end

(** Add the paint_action function as a listener to the paint_canvas *)
;; paint_canvas_controller.add_event_listener paint_action
;; slider_canvas_controller.add_event_listener slider_action
;; slider_canvas_controller2.add_event_listener slider_action2
;; slider_canvas_controller3.add_event_listener slider_action3
(* adds the even listener to each slider canvas*)
(**************************************)
(** TOOLBARS AND PAINT PROGRAM LAYOUT *)
(**************************************)

(** This part of the program creates the other widgets for the paint
    program -- the buttons, color selectors, etc., and lays them out
    in the top - level window. *)
(* TODO: Tasks 1, 4, 5, and 6 involve adding new buttons or changing
   the layout of the Paint GUI. Initially the layout is ugly because
   we use only the hpair widget demonstrated in Lecture. Task 1 is to
   make improvements to make the layout more appealing. You may choose
   to arrange the buttons and other GUI elements of the paint program
   however you like (so long as it is easily apparent how to use the
   interface).  The sample screenshot of our solution shows one
   possible design.  Also, feel free to improve the visual components
   of the GUI; for example, our solution puts borders around the
   buttons and uses a custom "color button" that changes its
   appearance based on whether or not the color is currently
   selected. *)

(** Create the Undo button *)
let (w_undo, lc_undo, nc_undo) = button "Undo"

let (w_line, lc_line, nc_line) = button "Lines"

let (w_points, lc_points, nc_points) = button "Points"


let (w_elipse, lc_elipse, nc_elipse) = button "Ellipse"




(** This function runs when the Undo button is clicked.
    It simply removes the last shape from the shapes deque. *)
(* TODO: You need to modify this in Task 3 and 4, and potentially 2
   (depending on your implementation). *)



let undo () : unit =
  if Deque.is_empty paint.shapes then () else
    ignore (Deque.remove_tail paint.shapes)

;; nc_undo.add_event_listener (mouseclick_listener undo)

let lines () : unit =
paint.mode <- LineStartMode
;; nc_line.add_event_listener (mouseclick_listener lines)


let points () : unit =
paint.mode <- PointMode
;; nc_points.add_event_listener (mouseclick_listener points)

let ellipse () : unit =
paint.mode <- ElipseMode
;; nc_elipse.add_event_listener (mouseclick_listener ellipse)






(** A spacer widget *)
let spacer : widget = space (10,10)


(** The mode toolbar, initially containing just the Undo button.
    TODO: you will need to modify this widget to add more buttons 
    to the toolbar in Tasks 5, and possibly 6. *)
let mode_toolbar : widget = 
hlist[w_undo; spacer; w_line; spacer; w_points; spacer; w_elipse; spacer; w]

(* The color selection toolbar. *)
(* This toolbar contains an indicator for the currently selected color
   and some buttons for changing it. Both the indicator and the buttons
   are small square widgets built from this higher-order function. *)
(** Create a widget that displays itself as colored square with the given
    width and color specified by the [get_color] function. *)
let colored_square (width:int) (get_color:unit -> color)
  : widget * notifier_controller =
  let repaint_square (gc:gctx) =
    let c = get_color () in
    fill_rect (with_color gc c) (0, 0) (width-1, width-1) in
  canvas (width,width) repaint_square

(** The color_indicator repaints itself with the currently selected
    color of the paint application. *)
let color_indicator =
  let indicator,_ = colored_square 24 (fun () -> paint.color) in
  let lab, _ = label "Current Color" in
  border (hlist [lab; space(10,10); indicator])

(** color_buttons repaint themselves with whatever color they were created
    with. They are also installed with a mouseclick listener
    that changes the selected color of the paint app to their color. 
    
     hpair (hpair color_indicator spacer)
   (hpair (hpair (color_button black) spacer)
   (hpair (hpair (color_button white) spacer)
   (hpair (hpair (color_button red) spacer)
   (hpair (hpair (color_button green) spacer)
   (hpair (hpair (color_button blue) spacer)
   (hpair (hpair (color_button yellow) spacer)
   (hpair (hpair (color_button cyan) spacer)
   (color_button magenta))))))))
   
   *)


let color_button (c: color) : widget =
  let w,nc = colored_square 10 (fun () -> c) in



  nc.add_event_listener (mouseclick_listener (fun () ->
      paint.color <- c ; 
      paint. preview2<- Some(Rect {color = red; width = 0;  starting = (0,0);
       hori = ((300*25*c.r)/(255*25)); verti = 25}); 
      paint. preview3<- Some(Rect {color = green; width = 0;  starting = (0,0); 
      hori = ((300*25*c.g)/(255*25)); verti = 25});
      paint. preview4<- Some(Rect {color = blue; width = 0;  starting = (0,0); 
      hori = ((300*25*c.b)/(255*25)); verti = 25});));
  w

(** The color selection toolbar. Contains the color indicator and
    buttons for several different colors. *)
(* TODO: Task 1 - This code contains a great deal of boilerplate.  You
     should come up with a better, more elegant, more concise solution... *)  
   let color_toolbar : widget = 
   hlist([color_button black; spacer; color_button white; spacer; 
   color_button red; spacer; color_button green; spacer;
   color_button blue; spacer; color_button yellow; spacer; color_button cyan; 
   spacer;
   color_button magenta])
   

(** The top-level paint program widget: a combination of the
    mode_toolbar, the color_toolbar and the paint_canvas widgets. *)
(* TODO: Task 1 (and others) involve modifing this layout to add new
   buttons and make the layout more aesthetically appealing. *)

let(instruct, lc) = label "3 Color Sliders Below(RGB): Click the slider, 
hold mouse down, and drag"

let(instruct2, lc) = label 
"Mouse drag and drop must be within canvas or else shape won't be drawn"


let paint_widget  = vlist([instruct2; paint_canvas; space(5,5); 
mode_toolbar;  
hlist[space(1,1);color_toolbar]; space(10,10); instruct; 
space(5,5); slider_canvas; space(5,5); slider_canvas2; space(5,5); 
hpair(slider_canvas3)(hpair(space(5,5))(color_indicator));])



(**************************************)
(**      Start the application        *)
(**************************************)

(** Run the event loop to process user events. *)
;; Eventloop.run paint_widget
