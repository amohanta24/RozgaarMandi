



import { HttpErrorResponse, HttpEvent, HttpHandlerFn, HttpInterceptorFn, HttpRequest } from "@angular/common/http";
import { inject } from "@angular/core";
import { MatDialog } from "@angular/material/dialog";
import { catchError, Observable, throwError } from "rxjs";
import { ProblemDetails } from "../models/ProlemDetails";
import { ErrorDialogComponen } from "../components/error-dialog-component/error-dialog-component";

export const AuthInterceptor : HttpInterceptorFn = (req,next) => {
  const dialog = inject(MatDialog);
     const jwt  = sessionStorage.getItem('token');
    if(jwt){
      const clonedReq = req.clone({
        setHeaders: {
          Authorization: `Bearer ${jwt}`
        }
      });
      return handleError(clonedReq,next,dialog);
    }
      return handleError(req,next,dialog);
  }

  function handleError(req: HttpRequest<any>, next: HttpHandlerFn, dialog: MatDialog): Observable<HttpEvent<any>> {

    let message : string = 'Unexpected Error Occurred';

   return next(req).pipe(
      catchError((error: HttpErrorResponse) => {
        let message = 'An unexpected error occurred.';
    
        if (isProblemDetails(error.error)) {
          const problemDetails: ProblemDetails = error.error;
          message = problemDetails?.detail ?? message;
        } else if (error.status === 0) {
          message = 'Server is not reachable. Please check your internet connection.';
        }
    
        if (dialog.openDialogs.length === 0) {
          dialog.open(ErrorDialogComponen, {
            data: { message },
            width: '400px'
          });
        }

        return throwError(() => error);
      })
    );
    }

     function isProblemDetails(obj: any): obj is ProblemDetails {
      return (
        obj &&
        typeof obj === 'object' &&
        ('detail' in obj || 'title' in obj || 'status' in obj)
      );
    }
    



